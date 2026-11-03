package com.example.gamercornerapp.ui.Screens.reviewDetail

import com.example.gamercornerapp.data.FeedPost

data class ReviewDetailState(
    val post: FeedPost? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
