package com.example.gamercornerapp.ui.Screens.chatDetail

import com.example.gamercornerapp.data.ChatMessage
import com.example.gamercornerapp.data.UserProfile

data class ChatDetailUiState(
    val messages: List<ChatMessage> = emptyList(),
    val otherUser: UserProfile? = null,
    val currentUserId: String = "",
    val inputText: String = "",
    val isLoadingMessages: Boolean = true,
    val isLoadingUser: Boolean = true,
    val errorMessage: String? = null
)
