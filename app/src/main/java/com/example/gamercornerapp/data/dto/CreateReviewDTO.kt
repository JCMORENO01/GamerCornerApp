package com.example.gamercornerapp.data.dto

data class CreateReviewDTO(
    val description: String,
    val rating: Float,
    val tags: List<String>? = emptyList(),
    val userId: Int,
    val gameId: Int
)
