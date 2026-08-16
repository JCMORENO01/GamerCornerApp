package com.example.gamercornerapp.ui.model

data class FeedPost(
    val id: String,
    val author: UserProfile,
    val relativeTime: String,
    val game: Game,
    val rating: Double,
    val description: String,
    val tags: List<String> = emptyList(),
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false
)