package com.example.gamercornerapp.data.datasource.impl.firestore

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.gamercornerapp.MainActivity
import com.example.gamercornerapp.data.Chat
import com.example.gamercornerapp.data.ChatMessage
import com.example.gamercornerapp.data.datasource.ChatRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class ChatFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    @param:ApplicationContext private val context: Context
) : ChatRemoteDataSource {

    override fun listenUserChats(userId: String): Flow<List<Chat>> = callbackFlow {
        val lastSeenTimes = mutableMapOf<String, com.google.firebase.Timestamp>()
        var isInitial = true

        val listener = db.collection("chats")
            .whereArrayContains("participants", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val chats = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Chat::class.java)?.copy(id = doc.id)
                    }.sortedByDescending { it.lastMessageTime }

                    chats.forEach { chat ->
                        val chatId = chat.id
                        val lastTime = chat.lastMessageTime
                        val senderId = chat.lastMessageSenderId

                        if (isInitial) {
                            lastSeenTimes[chatId] = lastTime
                        } else {
                            val previousTime = lastSeenTimes[chatId]
                            if (previousTime == null || lastTime.seconds > previousTime.seconds) {
                                lastSeenTimes[chatId] = lastTime
                                if (senderId.isNotBlank() && senderId != userId) {
                                    val senderName = chat.otherParticipantName.ifBlank { "Nuevo mensaje" }
                                    showChatNotification(context, senderName, chat.lastMessage)
                                }
                            }
                        }
                    }

                    isInitial = false
                    trySend(chats)
                }
            }

        awaitClose { listener.remove() }
    }

    override fun listenMessages(chatId: String): Flow<List<ChatMessage>> = callbackFlow {
        val listener = db.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.ASCENDING)
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

    override suspend fun sendMessage(
        chatId: String,
        senderId: String,
        receiverId: String,
        text: String,
        imageUrl: String?
    ) {
        val chatRef = db.collection("chats").document(chatId)
        val messagesRef = chatRef.collection("messages").document()

        val timestamp = com.google.firebase.Timestamp.now()
        val messageText = text.ifBlank { if (imageUrl != null) "📷 Foto" else "" }
        val message = ChatMessage(
            id = messagesRef.id,
            senderId = senderId,
            text = text,
            imageUrl = imageUrl,
            timestamp = timestamp
        )

        db.runTransaction { transaction ->
            val chatDoc = transaction.get(chatRef)
            
            if (!chatDoc.exists()) {
                val newChat = hashMapOf(
                    "participants" to listOf(senderId, receiverId),
                    "lastMessage" to messageText,
                    "lastMessageTime" to timestamp,
                    "lastMessageSenderId" to senderId
                )
                transaction.set(chatRef, newChat)
            } else {
                transaction.update(
                    chatRef,
                    mapOf(
                        "lastMessage" to messageText,
                        "lastMessageTime" to timestamp,
                        "lastMessageSenderId" to senderId
                    )
                )
            }
            
            transaction.set(messagesRef, message)
            null
        }.await()
    }

    override suspend fun uploadChatImage(chatId: String, imageUri: Uri): String {
        val fileName = "${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg"
        val storageRef = storage.reference.child("chat_images/$chatId/$fileName")
        storageRef.putFile(imageUri).await()
        return storageRef.downloadUrl.await().toString()
    }

    private fun showChatNotification(context: Context, senderName: String, messageText: String) {
        val channelId = "gamercorner_chat_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Mensajes de Chat",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notificaciones de nuevos mensajes de chat"
        }
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle(senderName)
            .setContentText(messageText)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
