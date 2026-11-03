package com.example.gamercornerapp.ui.Screens.reviewDetail

import com.example.gamercornerapp.data.Comment
import com.example.gamercornerapp.data.FeedPost

data class ReviewDetailState(
    val post: FeedPost? = null,
    val comments: List<Comment> = emptyList(),
    val commentInput: String = "",
    val isLoading: Boolean = false,
    val isLoadingComment: Boolean = false,
    val errorMessage: String? = null
)
