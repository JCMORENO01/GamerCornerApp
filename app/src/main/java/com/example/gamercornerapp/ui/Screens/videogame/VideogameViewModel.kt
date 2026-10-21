package com.example.gamercornerapp.ui.Screens.videogame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.GameRatingBar
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.repository.GameRepository
import com.example.gamercornerapp.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideogameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideogameState())

    val uiState: StateFlow<VideogameState> =
        _uiState.asStateFlow()

    private var currentGameId: String? = null

    fun loadGame(gameId: String) {
        currentGameId = gameId

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            val gameResult = gameRepository.getGameById(gameId)
            val reviewsResult = reviewRepository.getReviewsByGame(gameId)

            if (gameResult.isSuccess && reviewsResult.isSuccess) {
                val game = gameResult.getOrNull()
                val reviews = reviewsResult.getOrNull()

                if (game != null && reviews != null) {
                    val updatedGame = calculateUpdatedGame(game, reviews)
                    _uiState.update {
                        it.copy(
                            game = updatedGame,
                            reviews = reviews,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "No se pudo cargar el videojuego"
                        )
                    }
                }
            } else {
                val error = gameResult.exceptionOrNull()
                    ?: reviewsResult.exceptionOrNull()

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

    fun refreshReviews() {
        val gameId = currentGameId ?: return

        viewModelScope.launch {
            val result = reviewRepository.getReviewsByGame(gameId)

            if (result.isSuccess) {
                val reviews = result.getOrNull() ?: emptyList()
                _uiState.update { state ->
                    val currentGame = state.game
                    val updatedGame = if (currentGame != null) {
                        calculateUpdatedGame(currentGame, reviews)
                    } else null

                    state.copy(
                        game = updatedGame ?: state.game,
                        reviews = reviews,
                        error = null
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        error = result.exceptionOrNull()
                            ?.localizedMessage
                            ?: "No se pudieron actualizar las reseñas"
                    )
                }
            }
        }
    }

    fun deleteReview(reviewId: String) {
        viewModelScope.launch {
            val result = reviewRepository.deleteReview(reviewId)

            if (result.isSuccess) {
                _uiState.update { state ->
                    val newReviews = state.reviews.filter {
                        it.id != reviewId
                    }
                    val currentGame = state.game
                    val updatedGame = if (currentGame != null) {
                        calculateUpdatedGame(currentGame, newReviews)
                    } else null

                    state.copy(
                        game = updatedGame ?: state.game,
                        reviews = newReviews,
                        error = null
                    )
                }
            } else {
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

    private fun calculateUpdatedGame(
        game: Game,
        reviews: List<ReviewItem>
    ): Game {
        if (reviews.isEmpty()) return game

        val totalCount = reviews.size
        val averageRating = reviews.map { it.rating }.average()

        val countsByStar = reviews.groupingBy { it.rating }.eachCount()
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
