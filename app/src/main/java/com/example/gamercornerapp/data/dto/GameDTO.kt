package com.example.gamercornerapp.data.dto

data class GameDTO(
    val id: String = "",
    val title: String = "",
    val developer: String = "",
    val year: Int = 0,
    val image: String? = null,
    val description: String? = null,
    val tags: List<String>? = emptyList(),
    val rating: Double? = null,
    val reviewsCount: Int? = null
)
