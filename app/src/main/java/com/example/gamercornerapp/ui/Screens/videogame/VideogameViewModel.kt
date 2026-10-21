package com.example.gamercornerapp.ui.Screens.videogame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.GameRatingBar
import com.example.gamercornerapp.data.ReviewItem
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
    private val reviewRepository: ReviewRepository
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

            if (gameResult.isSuccess) {
                val game = gameResult.getOrThrow()

                _uiState.update {
                    it.copy(
                        game = game,
                        isLoading = false,
                        error = null
                    )
                }

                // Suscribirse a las reseñas en tiempo real
                reviewsJob = viewModelScope.launch {
                    reviewRepository.listenReviewsByGame(gameId)
                        .catch { error ->
                            _uiState.update {
                                it.copy(
                                    error = error.localizedMessage
                                        ?: "Error al escuchar reseñas"
                                )
                            }
                        }
                        .collect { reviews ->
                            _uiState.update { state ->
                                val baseGame = state.game ?: game
                                val updatedGame = calculateUpdatedGame(baseGame, reviews)
                                state.copy(
                                    game = updatedGame,
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
