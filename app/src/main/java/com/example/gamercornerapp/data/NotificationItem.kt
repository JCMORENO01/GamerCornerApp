package com.example.gamercornerapp.data

import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType

data class NotificationItem(
    val id: String,
    val username: String,
    val userAvatarImage: Int,
    val type: NotificationType,
    val actionText: String,      //si comentó, si comenzó a seguir a la persona
    val targetTitle: String? = null, //los nombres de los videojuegos si es de una reseña
    val commentQuote: String? = null, //solo si es de una reseña
    val relativeTime: String     //TODO- Revisar mas adelante
)
