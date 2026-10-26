package com.example.gamercornerapp.data.datasource.impl.firestore

import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.NotificationItem
import com.example.gamercornerapp.data.datasource.NotificationRemoteDataSource
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class NotificationFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : NotificationRemoteDataSource {

    override fun listenNotifications(
        userId: String
    ): Flow<List<NotificationItem>> = callbackFlow {

        val registration = db.collection("users")
            .document(userId)
            .collection("notifications")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("es"))

                val items = snapshot?.documents?.map { doc ->
                    val avatarUrl = doc.getString("userAvatarUrl")
                        ?: doc.getString("userPictureUrl")
                        ?: doc.getString("profilePictureUrl")

                    val typeStr = doc.getString("type") ?: "LIKE"
                    val notificationType = try {
                        NotificationType.valueOf(typeStr.uppercase())
                    } catch (_: Exception) {
                        NotificationType.LIKE
                    }

                    val timestamp = doc.getTimestamp("createdAt")?.toDate() ?: doc.getDate("createdAt")
                    val formattedTime = if (timestamp != null) {
                        formatter.format(timestamp)
                    } else {
                        "Reciente"
                    }

                    NotificationItem(
                        id = doc.id,
                        username = doc.getString("username")
                            ?: "Usuario",
                        userAvatarImage = if (avatarUrl.isNullOrBlank()) R.drawable.background_maquinitas else 0,
                        userAvatarUrl = avatarUrl,
                        type = notificationType,
                        actionText = doc.getString("actionText")
                            ?: "interactuó con tu contenido",
                        targetTitle = doc.getString("targetTitle")
                            ?: "",
                        relativeTime = formattedTime
                    )
                }.orEmpty()

                trySend(items)
            }

        awaitClose {
            registration.remove()
        }
    }
}
