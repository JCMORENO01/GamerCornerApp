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
            val games = if (result.isSuccess) {
                result.getOrNull() ?: emptyList()
            } else {
                emptyList()
            }
            val loadedGames = if (games.isNotEmpty()) games else LocalDataProvider.exploreResults
            val populars = if (games.isNotEmpty()) games else LocalDataProvider.popularGames
            val categories = LocalDataProvider.exploreCategories

            _uiState.update {
                it.copy(
                    popularGames = populars,
                    categories = categories,
                    allGames = loadedGames,
                    selectedCategory = "",
                    isLoading = false
                )
            }
            applyFiltersAndSort()
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        applyFiltersAndSort()
    }

    fun onCategorySelected(category: String) {
        _uiState.update { 
            val newCategory = if (it.selectedCategory == category) "" else category
            it.copy(selectedCategory = newCategory)
        }
        applyFiltersAndSort()
    }

    fun onShowFilterDialog(show: Boolean) {
        _uiState.update { it.copy(showFilterDialog = show) }
    }

    fun onSortOptionSelected(sortOption: SortOption) {
        _uiState.update { it.copy(currentSortOption = sortOption, showFilterDialog = false) }
        applyFiltersAndSort()
    }

    fun onResetFilters() {
        _uiState.update { 
            it.copy(
                searchQuery = "",
                selectedCategory = "",
                currentSortOption = SortOption.DEFAULT,
                showFilterDialog = false
            )
        }
        applyFiltersAndSort()
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

    private fun applyFiltersAndSort() {
        val state = _uiState.value
        val currentAllGames = state.allGames.ifEmpty { LocalDataProvider.exploreResults }
        val query = state.searchQuery.trim().lowercase()
        val category = state.selectedCategory
        val sort = state.currentSortOption

        var filtered = currentAllGames.filter { game ->
            val matchesQuery = query.isEmpty() || 
                game.title.lowercase().contains(query) || 
                game.developer.lowercase().contains(query)
            
            val matchesCategory = category.isEmpty() || category == "Todos" ||
                game.tags.any { it.equals(category, ignoreCase = true) }

            matchesQuery && matchesCategory
        }

        filtered = when (sort) {
            SortOption.DEFAULT -> filtered
            SortOption.HIGHEST_RATING -> filtered.sortedByDescending { it.rating }
            SortOption.NEWEST -> filtered.sortedByDescending { it.year }
            SortOption.ALPHABETICAL -> filtered.sortedBy { it.title }
        }

        _uiState.update { it.copy(resultGames = filtered) }
    }
}
