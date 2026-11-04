package com.example.gamercornerapp.ui.Screens.selectGame

import com.example.gamercornerapp.data.Game

data class SelectGameState(
    val searchQuery: String = "",
    val games: List<Game> = emptyList(),
    val filteredGames: List<Game> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
