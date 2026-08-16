package com.example.gamercornerapp.ui.model


data class FeedPost(
    val id: String,
    val username: String,
    val userAvatarImage: Int,
    val relativeTime: String,
    val gameTitle: String,
    val rating: Double,
    val description: String,
    val gameImageId: Int,
    val tags: List<String> = emptyList(),
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false
)
