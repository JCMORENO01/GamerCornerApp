package com.example.gamercornerapp.data

data class Game(
    val id: Int,
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

data class GameRatingBar(
    val stars: Int,
    val percentage: Float
)