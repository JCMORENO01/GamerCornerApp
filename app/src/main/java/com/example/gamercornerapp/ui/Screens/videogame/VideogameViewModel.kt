package com.example.gamercornerapp.ui.Screens.videogame

import androidx.lifecycle.ViewModel
import com.example.gamercornerapp.data.local.LocalDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VideogameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VideogameState())
    val uiState: StateFlow<VideogameState> = _uiState.asStateFlow()

    fun loadGame(gameId: Int) {
        val game = LocalDataProvider.getGameById(gameId)
        _uiState.update { it.copy(game = game) }
    }
}