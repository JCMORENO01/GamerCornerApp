package com.example.gamercornerapp.ui.Screens.chatList

import com.example.gamercornerapp.data.Chat

data class ChatListUiState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
