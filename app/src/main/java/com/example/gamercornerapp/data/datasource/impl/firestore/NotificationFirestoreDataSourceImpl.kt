
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

                val items = snapshot?.documents?.map { doc ->
                    NotificationItem(
                        id = doc.id,
                        username = doc.getString("username")
                            ?: "Usuario",
                        userAvatarImage = R.drawable.messi1,
                        type = NotificationType.LIKE,
                        actionText = doc.getString("actionText")
                            ?: "interactuó con tu contenido",
                        targetTitle = doc.getString("targetTitle")
                            ?: "",
                        relativeTime = "Reciente"
                    )
                }.orEmpty()

                trySend(items)
            }

        awaitClose {
            registration.remove()
        }
    }
}
