package com.example.gamercornerapp.data.dto

data class GameDTO(
    val id: String,
    val title: String,
    val developer: String,
    val year: Int,
    val image: String?, // Hacerlo nullable por si acaso
    val description: String?,
    val tags: List<String>? = emptyList(), // Hacemos la lista nullable por si no viene del servidor
    val rating: Double? = null,
    val reviewsCount: Int? = null
)
