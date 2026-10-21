
package com.example.gamercornerapp.ui.Screens.videogame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    _uiState.update {
                        it.copy(
                            game = game,
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
                _uiState.update {
                    it.copy(
                        reviews = result.getOrNull() ?: emptyList(),
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
                    state.copy(
                        reviews = state.reviews.filter {
                            it.id != reviewId
                        },
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
}
