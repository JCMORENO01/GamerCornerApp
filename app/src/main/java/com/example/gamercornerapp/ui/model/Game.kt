package com.example.gamercornerapp.ui.model

data class Game(
    val title: String,
    val developer: String,
    val year: Int,
    val image: Int,
    val rating: Double = 0.0,
    val reviewsCount: Int = 0,
    val tags: List<String> = emptyList(),
    val description: String = "",
    val ratingDistribution: List<GameRatingBar> = emptyList()
)

// Representa una fila de la barra de "Calificación de la comunidad"
// stars: 5,4,3,2 o 1 · percentage: qué tan llena se ve la barra (0f a 1f)
data class GameRatingBar(
    val stars: Int,
    val percentage: Float
)