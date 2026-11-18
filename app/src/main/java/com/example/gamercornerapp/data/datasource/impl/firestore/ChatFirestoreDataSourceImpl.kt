package com.example.gamercornerapp.data.datasource.impl.firestore

import com.example.gamercornerapp.data.Chat
import com.example.gamercornerapp.data.ChatMessage
import com.example.gamercornerapp.data.datasource.ChatRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ChatRemoteDataSource {

    override fun listenUserChats(userId: String): Flow<List<Chat>> = callbackFlow {
        val listener = db.collection("chats")
            .whereArrayContains("participants", userId)
            .orderBy("lastMessageTime", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val chats = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Chat::class.java)?.copy(id = doc.id)
                    }
                    trySend(chats)
                }
            }

        awaitClose { listener.remove() }
    }

    override fun listenMessages(chatId: String): Flow<List<ChatMessage>> = callbackFlow {
        val listener = db.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val messages = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ChatMessage::class.java)?.copy(id = doc.id)
                    }
                    trySend(messages)
                }
            }

        awaitClose { listener.remove() }
    }

    override suspend fun sendMessage(chatId: String, senderId: String, receiverId: String, text: String) {
        val chatRef = db.collection("chats").document(chatId)
        val messagesRef = chatRef.collection("messages").document()

        val timestamp = com.google.firebase.Timestamp.now()
        val message = ChatMessage(
            id = messagesRef.id,
            senderId = senderId,
            text = text,
            timestamp = timestamp
        )

        db.runTransaction { transaction ->
            val chatDoc = transaction.get(chatRef)
            
            // Si el chat no existe (primer mensaje), lo creamos con metadata básica.
            // Nota: Nombres/Fotos se resuelven a nivel Repository/UI para mantenerse actualizados,
            // aquí solo guardamos los IDs en participants para las queries.
            if (!chatDoc.exists()) {
                val newChat = hashMapOf(
                    "participants" to listOf(senderId, receiverId),
                    "lastMessage" to text,
                    "lastMessageTime" to timestamp
                )
                transaction.set(chatRef, newChat)
            } else {
                transaction.update(
                    chatRef,
                    mapOf(
                        "lastMessage" to text,
                        "lastMessageTime" to timestamp
                    )
                )
            }
            
            transaction.set(messagesRef, message)
            null
        }.await()
    }
}
