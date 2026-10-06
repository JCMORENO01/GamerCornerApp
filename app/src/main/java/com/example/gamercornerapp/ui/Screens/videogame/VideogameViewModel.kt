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
    val uiState: StateFlow<VideogameState> = _uiState.asStateFlow()

    fun loadGame(gameId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

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
                }
            } else {
                val error = gameResult.exceptionOrNull() ?: reviewsResult.exceptionOrNull()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error?.localizedMessage
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
                    val newReviews = mutableListOf<com.example.gamercornerapp.data.ReviewItem>()
                    for (review in state.reviews) {
                        if (review.id != reviewId) {
                            newReviews.add(review)
                        }
                    }
                    state.copy(reviews = newReviews)
                }
            } else {
                _uiState.update { it.copy(error = result.exceptionOrNull()?.localizedMessage) }
            }
        }
    }
}
