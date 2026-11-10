
package com.example.gamercornerapp.data.dto

data class NoteDTO(
    val id: String = "",
    val userId: String = "",
    val username: String = "",
    val profilePictureUrl: String? = null,
    val text: String = "",
    val createdAtMillis: Long = 0L,
    val expiresAtMillis: Long = 0L
)
