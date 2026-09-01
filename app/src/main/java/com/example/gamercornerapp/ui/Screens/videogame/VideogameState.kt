package com.example.gamercornerapp.ui.Screens.videogame

import com.example.gamercornerapp.data.Game

data class VideogameState(
    val game: Game? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)