
package com.example.gamercornerapp.data.dto

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

    // Cantidad de likes almacenada en Firestore
    val likesCount: Long = 0L,

    // Indica si el usuario autenticado dio like
    val isLiked: Boolean = false
)
