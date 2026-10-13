package com.example.gamercornerapp.data.dto

data class GameDTO(
    val id: String,
    val title: String,
    val developer: String,
    val year: Int,
    val image: String?,
    val description: String?,
    val tags: List<String>? = emptyList(),
    val rating: Double? = null,
    val reviewsCount: Int? = null
)
