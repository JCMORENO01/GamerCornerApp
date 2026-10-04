package com.example.gamercornerapp.ui.Screens.videogame

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
class VideogameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(VideogameState())
    val uiState: StateFlow<VideogameState> = _uiState.asStateFlow()

    fun loadGame(gameId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val gameResult = gameRepository.getGameById(gameId.toString())
            val reviewResult = reviewRepository.getReviewsByGame(gameId.toString())

            val game = gameResult.getOrElse { LocalDataProvider.getGameById(gameId) }
            val reviews = reviewResult.getOrElse { LocalDataProvider.reviews }

            _uiState.update {
                it.copy(
                    game = game,
                    reviews = reviews,
                    isLoading = false
                )
            }
        }
    }

    fun deleteReview(reviewId: String, gameId: Int) {
        viewModelScope.launch {
            reviewRepository.deleteReview(reviewId).onSuccess {
                loadGame(gameId)
            }
        }
    }
}
