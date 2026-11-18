package com.example.gamercornerapp.data.datasource.impl.firestore

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.gamercornerapp.MainActivity
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.NotificationItem
import com.example.gamercornerapp.data.datasource.NotificationRemoteDataSource
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class NotificationFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    @param:ApplicationContext private val context: Context
) : NotificationRemoteDataSource {

    override fun listenNotifications(
        userId: String
    ): Flow<List<NotificationItem>> = callbackFlow {

        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("es"))

        var firestoreNotifications = emptyList<NotificationItem>()
        var followNotifications = emptyList<NotificationItem>()
        var isInitialFollowersLoad = true
        val knownFollowerIds = mutableSetOf<String>()

        fun emitCombined() {
            val combined = (firestoreNotifications + followNotifications)
                .sortedByDescending { it.timestampMs }
            trySend(combined)
        }

        fun processDocs(
            docs: List<DocumentSnapshot>,
            userMap: Map<String, DocumentSnapshot>
        ) {
            firestoreNotifications = docs.map { doc ->
                val fromUserId = doc.getString("fromUserId")
                val userDoc = fromUserId?.let { userMap[it] }

                val avatarUrl = doc.getString("userAvatarUrl")
                    ?: doc.getString("userPictureUrl")
                    ?: doc.getString("profilePictureUrl")
                    ?: userDoc?.getString("profilePictureUrl")

                val avatarImageId = doc.getLong("profileImageId")?.toInt()
                    ?: doc.getLong("userAvatarImage")?.toInt()
                    ?: userDoc?.getLong("profileImageId")?.toInt()
                    ?: 0

                val typeStr = doc.getString("type") ?: "LIKE"
                val notificationType = try {
                    NotificationType.valueOf(typeStr.uppercase())
                } catch (_: Exception) {
                    NotificationType.LIKE
                }

                val date = doc.getTimestamp("createdAt")?.toDate() ?: doc.getDate("createdAt")
                val timeMs = date?.time ?: 0L
                val formattedTime = if (date != null) formatter.format(date) else "Reciente"

                val resolvedUsername = doc.getString("username")
                    ?: userDoc?.getString("username")
                    ?: "Usuario"

                NotificationItem(
                    id = doc.id,
                    username = resolvedUsername,
                    userAvatarImage = if (avatarUrl.isNullOrBlank() && avatarImageId == 0) R.drawable.messi1 else 0,
                    userAvatarUrl = avatarUrl,
                    userAvatarResource = avatarImageId.takeIf { it != 0 },
                    type = notificationType,
                    actionText = doc.getString("actionText") ?: "interactuó con tu contenido",
                    targetTitle = doc.getString("targetTitle") ?: "",
                    relativeTime = formattedTime,
                    timestampMs = timeMs
                )
            }
            emitCombined()
        }

        // 1. Escuchar la colección de notificaciones en Firestore y enriquecer avatares de los usuarios
        val notifRegistration = db.collection("users")
            .document(userId)
            .collection("notifications")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val rawDocs = snapshot?.documents.orEmpty()
                if (rawDocs.isEmpty()) {
                    firestoreNotifications = emptyList()
                    emitCombined()
                    return@addSnapshotListener
                }

                val fromUserIds = rawDocs.mapNotNull { it.getString("fromUserId") }.distinct()

                if (fromUserIds.isEmpty()) {
                    processDocs(rawDocs, emptyMap())
                } else {
                    db.collection("users")
                        .whereIn(FieldPath.documentId(), fromUserIds.take(10))
                        .get()
                        .addOnSuccessListener { usersSnap ->
                            val userMap = usersSnap.documents.associateBy { it.id }
                            processDocs(rawDocs, userMap)
                        }
                        .addOnFailureListener {
                            processDocs(rawDocs, emptyMap())
                        }
                }
            }

        // 2. Escuchar la colección de seguidores (followers) para generar notificaciones de follow instantáneas en el cliente
        val followersRegistration = db.collection("users")
            .document(userId)
            .collection("followers")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val currentFollowerIds = snapshot?.documents?.map { it.id }.orEmpty()

                if (isInitialFollowersLoad) {
                    knownFollowerIds.addAll(currentFollowerIds)
                    isInitialFollowersLoad = false
                } else {
                    val newFollowerIds = currentFollowerIds.filter { it !in knownFollowerIds }
                    if (newFollowerIds.isNotEmpty()) {
                        knownFollowerIds.addAll(newFollowerIds)

                        db.collection("users")
                            .whereIn(FieldPath.documentId(), newFollowerIds.take(10))
                            .get()
                            .addOnSuccessListener { newUsersSnapshot ->
                                for (userDoc in newUsersSnapshot.documents) {
                                    val username = userDoc.getString("username") ?: "Un usuario"
                                    showFollowNotification(context, username)
                                }
                            }
                    }
                }

                if (currentFollowerIds.isEmpty()) {
                    followNotifications = emptyList()
                    emitCombined()
                    return@addSnapshotListener
                }

                db.collection("users")
                    .whereIn(FieldPath.documentId(), currentFollowerIds.take(10))
                    .get()
                    .addOnSuccessListener { usersSnapshot ->
                        followNotifications = usersSnapshot.documents.map { userDoc ->
                            val followerId = userDoc.id
                            val username = userDoc.getString("username") ?: "Usuario"
                            val profilePictureUrl = userDoc.getString("profilePictureUrl")
                            val profileImageId = userDoc.getLong("profileImageId")?.toInt() ?: 0

                            NotificationItem(
                                id = "follow_$followerId",
                                username = username,
                                userAvatarImage = if (profilePictureUrl.isNullOrBlank() && profileImageId == 0) R.drawable.messi1 else 0,
                                userAvatarUrl = profilePictureUrl,
                                userAvatarResource = profileImageId.takeIf { it != 0 },
                                type = NotificationType.FOLLOW,
                                actionText = "comenzó a seguirte",
                                targetTitle = "",
                                relativeTime = "Reciente",
                                timestampMs = System.currentTimeMillis()
                            )
                        }
                        emitCombined()
                    }
            }

        awaitClose {
            notifRegistration.remove()
            followersRegistration.remove()
        }
    }

    private fun showFollowNotification(context: Context, username: String) {
        val channelId = "gamercorner_activity_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Actividad y Seguidores",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notificaciones de nuevos seguidores y actividad"
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
            .setContentTitle("¡Nuevo seguidor en GamerCorner!")
            .setContentText("$username comenzó a seguirte.")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
