
package com.example.gamercornerapp.ui.Screens.notifications

import androidx.lifecycle.ViewModel
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.NotificationItem
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())
    val uiState: StateFlow<NotificationsState> = _uiState.asStateFlow()

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        val userId = auth.currentUser?.uid ?: return

        _uiState.update { it.copy(isLoading = true) }

        // Escuchamos en tiempo real la subcolección de notificaciones del usuario
        db.collection("users").document(userId)
            .collection("notifications")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.localizedMessage)
                    }
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        NotificationItem(
                            id = doc.id,
                            username = doc.getString("username") ?: "Usuario",
                            userAvatarImage = R.drawable.messi1, // Tu imagen por defecto
                            type = NotificationType.LIKE,
                            actionText = doc.getString("actionText")
                                ?: "interactuó con tu contenido",
                            targetTitle = doc.getString("targetTitle") ?: "",
                            relativeTime = "Reciente"
                        )
                    }

                    _uiState.update {
                        it.copy(
                            notifications = list,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
    }
}
