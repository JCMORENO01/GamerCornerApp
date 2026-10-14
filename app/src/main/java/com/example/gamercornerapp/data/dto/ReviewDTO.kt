
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
    val game: CreateReviewGameDTO? = null
) {
    constructor() : this("")
}
