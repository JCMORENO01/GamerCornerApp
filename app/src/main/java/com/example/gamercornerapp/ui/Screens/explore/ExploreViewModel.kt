package com.example.gamercornerapp.ui.Screens.explore

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
class ExploreViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExploreState())
    val uiState: StateFlow<ExploreState> = _uiState.asStateFlow()

    init {
        loadGames()
    }

    fun loadGames() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = gameRepository.getGames()
            result.onSuccess { games ->
                _uiState.update {
                    it.copy(
                        popularGames = if (games.isNotEmpty()) games else LocalDataProvider.popularGames,
                        categories = LocalDataProvider.exploreCategories,
                        resultGames = if (games.isNotEmpty()) games else LocalDataProvider.exploreResults,
                        selectedCategory = LocalDataProvider.exploreCategories.firstOrNull()?.name ?: "",
                        isLoading = false
                    )
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        popularGames = LocalDataProvider.popularGames,
                        categories = LocalDataProvider.exploreCategories,
                        resultGames = LocalDataProvider.exploreResults,
                        selectedCategory = LocalDataProvider.exploreCategories.firstOrNull()?.name ?: "",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onCategorySelected(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onFavoriteClick(game: Game) {
        _uiState.update {
            val newFavorites = if (it.favoriteGameTitles.contains(game.title)) {
                it.favoriteGameTitles - game.title
            } else {
                it.favoriteGameTitles + game.title
            }
            it.copy(favoriteGameTitles = newFavorites)
        }
    }
}
