package com.example.gamercornerapp.ui.Screens.videogame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.GameRatingBar
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.GameRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideogameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideogameState())

    val uiState: StateFlow<VideogameState> =
        _uiState.asStateFlow()

    private var currentGameId: String? = null
    private var reviewsJob: Job? = null

    fun loadGame(gameId: String) {
        if (currentGameId == gameId && _uiState.value.game != null) return
        currentGameId = gameId

        reviewsJob?.cancel()

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            val gameResult = gameRepository.getGameById(gameId)
            
            val userId = authRepository.currentUser?.uid
            val isSaved = if (userId != null && gameResult.isSuccess) {
                gameRepository.isGameSaved(gameId, userId).getOrDefault(false)
            } else {
                false
            }

            if (gameResult.isSuccess) {
                val game = gameResult.getOrThrow()

                // Suscribirse a las reseñas en tiempo real manteniendo isLoading = true hasta tener las reseñas listas
                reviewsJob = viewModelScope.launch {
                    reviewRepository.listenReviewsByGame(gameId)
                        .catch { error ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = error.localizedMessage
                                        ?: "Error al escuchar reseñas"
                                )
                            }
                        }
                        .collect { reviews ->
                            val updatedGame = calculateUpdatedGame(game, reviews)
                            _uiState.update { state ->
                                state.copy(
                                    game = updatedGame,
                                    isSaved = isSaved,
                                    reviews = reviews,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }
                }
            } else {
                val error = gameResult.exceptionOrNull()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error?.localizedMessage
                            ?: "No se pudo cargar el videojuego"
                    )
                }
            }
        }
    }

    fun toggleReviewLike(reviewId: String) {
        if (reviewId.isBlank()) return
        val userId = authRepository.currentUser?.uid ?: return

        // Actualización optimista
        val updatedReviews = _uiState.value.reviews.map { review ->
            if (review.id == reviewId) {
                val newIsLiked = !review.isLiked
                val newCount = if (newIsLiked) review.likesCount + 1 else (review.likesCount - 1).coerceAtLeast(0)
                review.copy(isLiked = newIsLiked, likesCount = newCount)
            } else {
                review
            }
        }

        val baseGame = _uiState.value.game
        val updatedGame = if (baseGame != null) calculateUpdatedGame(baseGame, updatedReviews) else null

        _uiState.update {
            it.copy(
                reviews = updatedReviews,
                game = updatedGame ?: it.game
            )
        }

        viewModelScope.launch {
            try {
                val result = reviewRepository.toggleReviewLike(reviewId, userId)
                if (result.isFailure) {
                    currentGameId?.let { loadGame(it) }
                }
            } catch (_: Exception) {
                currentGameId?.let { loadGame(it) }
            }
        }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReview(reviewId)

            if (result.isFailure) {
                _uiState.update {
                    it.copy(
                        error = result.exceptionOrNull()
                            ?.localizedMessage
                            ?: "No se pudo eliminar la reseña"
                    )
                }
            }
        }
    }
    
    fun toggleSaveReview(reviewId: String) {
        if (reviewId.isBlank()) return
        val userId = authRepository.currentUser?.uid ?: return

        // Actualización optimista
        val updatedReviews = _uiState.value.reviews.map { review ->
            if (review.id == reviewId) {
                review.copy(isBookmarked = !review.isBookmarked)
            } else {
                review
            }
        }

        _uiState.update {
            it.copy(reviews = updatedReviews)
        }

        viewModelScope.launch {
            try {
                val result = reviewRepository.toggleSaveReview(reviewId, userId)
                if (result.isFailure) {
                    currentGameId?.let { loadGame(it) }
                }
            } catch (_: Exception) {
                currentGameId?.let { loadGame(it) }
            }
        }
    }
    
    fun toggleSaveGame() {
        val gameId = currentGameId ?: return
        val userId = authRepository.currentUser?.uid ?: return
        
        // Optimistic UI update
        _uiState.update { 
            it.copy(isSaved = !it.isSaved)
        }
        
        viewModelScope.launch {
            try {
                val result = gameRepository.toggleSaveGame(gameId, userId)
                if (result.isFailure) {
                    _uiState.update { it.copy(isSaved = !it.isSaved) }
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(isSaved = !it.isSaved) }
            }
        }
    }

    private fun calculateUpdatedGame(
        game: Game,
        reviews: List<ReviewItem>
    ): Game {
        if (reviews.isEmpty()) return game

        val totalCount = reviews.size
        val averageRating = reviews.map { review -> review.rating.toDouble() }.average()

        val countsByStar = reviews.groupingBy { review -> review.rating }.eachCount()
        val distribution = (5 downTo 1).map { star ->
            val countForStar = countsByStar[star] ?: 0
            GameRatingBar(
                stars = star,
                percentage = countForStar.toFloat() / totalCount
            )
        }

        return game.copy(
            rating = averageRating,
            reviewsCount = totalCount,
            ratingDistribution = distribution
        )
    }
}
