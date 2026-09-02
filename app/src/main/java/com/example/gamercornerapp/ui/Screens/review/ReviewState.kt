package com.example.gamercornerapp.ui.Screens.review

import com.example.gamercornerapp.data.Game

data class ReviewState(
    val game: Game? = null,
    val rating: Int = 5,
    val opinion: String = ""
)