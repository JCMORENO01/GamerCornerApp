package com.example.gamercornerapp.ui.Screens.review

import androidx.lifecycle.ViewModel
import com.example.gamercornerapp.data.local.LocalDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReviewViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewState())
    val uiState: StateFlow<ReviewState> = _uiState.asStateFlow()

    fun loadGame(gameId: Int) {
        val game = LocalDataProvider.getGameById(gameId)
        _uiState.update { it.copy(game = game) }
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
}