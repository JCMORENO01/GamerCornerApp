package com.example.gamercornerapp.ui.Screens.explore

import androidx.lifecycle.ViewModel
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.local.LocalDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExploreViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ExploreState())
    val uiState: StateFlow<ExploreState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                popularGames = LocalDataProvider.popularGames,
                categories = LocalDataProvider.exploreCategories,
                resultGames = LocalDataProvider.exploreResults,
                selectedCategory = LocalDataProvider.exploreCategories.firstOrNull()?.name ?: ""
            )
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