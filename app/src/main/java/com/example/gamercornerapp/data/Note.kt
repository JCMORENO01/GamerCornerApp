
package com.example.gamercornerapp.data

data class Note(
    val id: String,
    val userId: String,
    val username: String,
    val profilePictureUrl: String?,
    val text: String,
    val createdAtMillis: Long,
    val expiresAtMillis: Long
)
