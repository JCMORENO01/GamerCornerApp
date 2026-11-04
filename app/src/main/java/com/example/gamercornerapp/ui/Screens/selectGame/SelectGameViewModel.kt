package com.example.gamercornerapp.ui.Screens.selectGame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.local.LocalDataProvider
import com.example.gamercornerapp.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectGameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SelectGameState())
    val uiState: StateFlow<SelectGameState> = _uiState.asStateFlow()

    init {
        loadGames()
    }

    fun loadGames() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = gameRepository.getGames()
            if (result.isSuccess) {
                val allGames = result.getOrDefault(emptyList()).ifEmpty { LocalDataProvider.popularGames }
                val currentQuery = _uiState.value.searchQuery
                _uiState.update { state ->
                    state.copy(
                        games = allGames,
                        filteredGames = filterGames(allGames, currentQuery),
                        isLoading = false
                    )
                }
            } else {
                val fallbackGames = LocalDataProvider.popularGames
                val currentQuery = _uiState.value.searchQuery
                _uiState.update { state ->
                    state.copy(
                        games = fallbackGames,
                        filteredGames = filterGames(fallbackGames, currentQuery),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                filteredGames = filterGames(state.games, query)
            )
        }
    }

    private fun filterGames(games: List<Game>, query: String): List<Game> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return games
        return games.filter { game ->
            game.title.contains(trimmed, ignoreCase = true) ||
            game.developer.contains(trimmed, ignoreCase = true) ||
            game.tags.any { tag -> tag.contains(trimmed, ignoreCase = true) }
        }
    }
}
