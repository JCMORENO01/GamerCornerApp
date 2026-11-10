
package com.example.gamercornerapp.data.dto

import com.google.firebase.firestore.PropertyName

data class ReviewDTO(
    val id: String = "",
    val description: String = "",
    val rating: Float = 0f,
    val tags: List<String> = emptyList(),
    val userId: String = "",
    val gameId: String = "",
    val createdAt: String = "",
    val user: CreateReviewUserDTO? = null,
    val game: CreateReviewGameDTO? = null,

    val likesCount: Long = 0L,

    @get:PropertyName("isLiked")
    val isLiked: Boolean = false,

    @get:PropertyName("isBookmarked")
    val isBookmarked: Boolean = false,

    val commentsCount: Long = 0L,

    // Nuevos campos para el mapa
    val latitude: Double? = null,
    val longitude: Double? = null,
    val createdAtMillis: Long? = null
)
