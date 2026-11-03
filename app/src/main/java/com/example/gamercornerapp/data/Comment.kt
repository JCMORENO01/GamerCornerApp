package com.example.gamercornerapp.data

import com.google.firebase.Timestamp

data class Comment(
    val commentId: String = "",
    val userId: String = "",
    val userName: String = "",
    val text: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
