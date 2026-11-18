package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.Chat
import com.example.gamercornerapp.data.ChatMessage
import com.example.gamercornerapp.data.datasource.ChatRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatDataSource: ChatRemoteDataSource,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    /**
     * Genera un ID de chat único y consistente entre dos usuarios
     * ordenando sus IDs alfabéticamente.
     */
    fun getChatId(userId1: String, userId2: String): String {
        return if (userId1 < userId2) {
            "${userId1}_${userId2}"
        } else {
            "${userId2}_${userId1}"
        }
    }

    /**
     * Escucha los chats del usuario actual.
     * Para cada chat, obtiene la info del otro participante (nombre, foto) desde UserRepository.
     */
    fun listenMyChats(): Flow<List<Chat>> {
        val currentUserId = authRepository.currentUser?.uid ?: ""
        
        return chatDataSource.listenUserChats(currentUserId).map { chats ->
            chats.map { chat ->
                val otherId = chat.participants.firstOrNull { it != currentUserId } ?: ""
                
                // Intentamos buscar la info del otro usuario
                val otherUserResult = userRepository.getUserById(otherId)
                val otherUser = otherUserResult.getOrNull()
                
                val displayName = otherUser?.username?.takeIf { it.isNotBlank() } 
                    ?: otherUser?.nickName?.takeIf { it.isNotBlank() }
                    ?: "Usuario Gamer"
                    
                chat.copy(
                    otherParticipantId = otherId,
                    otherParticipantName = displayName,
                    otherParticipantImage = otherUser?.profilePictureUrl
                )
            }
        }
    }

    fun listenMessages(chatId: String): Flow<List<ChatMessage>> {
        return chatDataSource.listenMessages(chatId)
    }

    suspend fun sendMessage(receiverId: String, text: String): Result<Unit> {
        return try {
            val currentUserId = authRepository.currentUser?.uid
                ?: throw Exception("Usuario no autenticado")
                
            require(text.isNotBlank()) { "El mensaje no puede estar vacío" }
            
            val chatId = getChatId(currentUserId, receiverId)
            
            chatDataSource.sendMessage(
                chatId = chatId,
                senderId = currentUserId,
                receiverId = receiverId,
                text = text
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
