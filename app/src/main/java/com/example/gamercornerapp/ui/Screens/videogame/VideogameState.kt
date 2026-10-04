package com.example.gamercornerapp.ui.Screens.videogame

import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.ReviewItem

data class VideogameState(
    val game: Game? = null,
    val reviews: List<ReviewItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
