package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.Chat
import com.example.gamercornerapp.data.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRemoteDataSource {
    fun listenUserChats(userId: String): Flow<List<Chat>>
    fun listenMessages(chatId: String): Flow<List<ChatMessage>>
    suspend fun sendMessage(
        chatId: String,
        senderId: String,
        receiverId: String,
        text: String
    )
}
