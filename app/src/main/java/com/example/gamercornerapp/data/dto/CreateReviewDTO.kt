
package com.example.gamercornerapp.data.dto

data class CreateReviewDTO(
    val description: String = "",
    val rating: Float = 0f,
    val tags: List<String>? = emptyList(),
    val userId: String = "",
    val gameId: String = "",
    val createdAt: String? = null,
    val user: CreateReviewUserDTO? = null,
    val game: CreateReviewGameDTO? = null,
    val likesCount: Long = 0L,

    // Ubicación opcional de la reseña
    val latitude: Double? = null,
    val longitude: Double? = null,
    val createdAtMillis: Long? = null
)
