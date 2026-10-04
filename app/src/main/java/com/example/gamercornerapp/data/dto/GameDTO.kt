package com.example.gamercornerapp.data.dto

data class GameDTO(
    val id: String,
    val title: String,
    val developer: String,
    val year: Int,
    val image: Int,
    val description: String?,
    val tags: List<String> = emptyList()
)
