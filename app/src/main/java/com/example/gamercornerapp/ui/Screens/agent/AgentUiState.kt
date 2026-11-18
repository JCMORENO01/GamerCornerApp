package com.example.gamercornerapp.ui.Screens.agent

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isFromUser: Boolean,
    val isLoading: Boolean = false
)

data class AgentUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isRecording: Boolean = false,
    val errorMessage: String? = null
)
