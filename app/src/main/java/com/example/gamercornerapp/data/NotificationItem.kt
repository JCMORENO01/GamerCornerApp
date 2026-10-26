package com.example.gamercornerapp.data

import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType

data class NotificationItem(
    val id: String,
    val username: String,
    val userAvatarImage: Int = com.example.gamercornerapp.R.drawable.messi1,
    val userAvatarUrl: String? = null,
    val type: NotificationType,
    val actionText: String,
    val targetTitle: String? = null,
    val commentQuote: String? = null,
    val relativeTime: String
)
