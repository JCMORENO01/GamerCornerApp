package com.example.gamercornerapp.ui.Screens.explore

import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.GameCategory

data class ExploreState(
    val popularGames: List<Game> = emptyList(),
    val categories: List<GameCategory> = emptyList(),
    val resultGames: List<Game> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String = "",
    val favoriteGameTitles: Set<String> = emptySet()
)