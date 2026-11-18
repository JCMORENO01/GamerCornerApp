package com.example.gamercornerapp.data

import com.google.firebase.Timestamp

data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val imageUrl: String? = null,
    val timestamp: Timestamp = Timestamp.now(),
    val isRead: Boolean = false
)

data class Chat(
    val id: String = "",
    val participants: List<String> = emptyList(),
    val otherParticipantId: String = "",
    val otherParticipantName: String = "",
    val otherParticipantImage: String? = null,
    val lastMessage: String = "",
    val lastMessageTime: Timestamp = Timestamp.now()
)
