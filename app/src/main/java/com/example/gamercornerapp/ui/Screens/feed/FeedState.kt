package com.example.gamercornerapp.ui.Screens.feed

import com.example.gamercornerapp.data.FeedPost

data class FeedState(
    val posts: List<FeedPost> = emptyList(),
    val selectedTabIndex: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
