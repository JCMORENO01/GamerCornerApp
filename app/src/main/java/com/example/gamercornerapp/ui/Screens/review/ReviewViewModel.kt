package com.example.gamercornerapp.ui.Screens.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.local.LocalDataProvider
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
class ReviewViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewState())
    val uiState: StateFlow<ReviewState> = _uiState.asStateFlow()

    fun loadGame(gameId: Int, reviewId: String? = null, initialOpinion: String? = null, initialRating: Int? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, reviewId = reviewId) }
            val gameResult = gameRepository.getGameById(gameId.toString())
            val game = gameResult.getOrElse { LocalDataProvider.getGameById(gameId) }

            _uiState.update {
                it.copy(
                    game = game,
                    opinion = initialOpinion ?: it.opinion,
                    rating = initialRating ?: it.rating,
                    isLoading = false
                )
            }
        }
    }

    fun onRatingChange(rating: Int) {
        _uiState.update { it.copy(rating = rating) }
    }

    fun onOpinionChange(opinion: String) {
        _uiState.update { it.copy(opinion = opinion) }
    }

    fun onTagToggle(tag: String) {
        _uiState.update { state ->
            val updatedTags = if (state.selectedTags.contains(tag)) {
                state.selectedTags - tag
            } else {
                state.selectedTags + tag
            }
            state.copy(selectedTags = updatedTags)
        }
    }

    fun publishReview(gameId: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val currentState = _uiState.value

            val result = if (currentState.reviewId != null) {
                reviewRepository.updateReview(
                    reviewId = currentState.reviewId,
                    description = currentState.opinion,
                    rating = currentState.rating,
                    tags = currentState.selectedTags.toList(),
                    userId = "1",
                    gameId = gameId.toString()
                )
            } else {
                reviewRepository.createReview(
                    description = currentState.opinion,
                    rating = currentState.rating,
                    tags = currentState.selectedTags.toList(),
                    userId = "1",
                    gameId = gameId.toString()
                )
            }

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isPublishSuccess = true) }
                onComplete()
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.localizedMessage) }
            }
        }
    }
}
