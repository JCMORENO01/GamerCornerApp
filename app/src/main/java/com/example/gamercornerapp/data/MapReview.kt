
package com.example.gamercornerapp.data

data class MapReview(
    val id: String,
    val gameTitle: String,
    val username: String,
    val description: String,
    val rating: Float,
    val latitude: Double,
    val longitude: Double,
    val createdAtMillis: Long
)
