package com.example.gamercornerapp.ui.Screens.review

import com.example.gamercornerapp.data.Game

data class ReviewState(
    val game: Game? = null,
    val reviewId: String? = null,
    val rating: Int = 5,
    val opinion: String = "",
    val selectedTags: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val isPublishSuccess: Boolean = false,
    val errorMessage: String? = null
)
