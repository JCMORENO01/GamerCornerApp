package com.example.gamercornerapp.ui.Screens.notifications

import com.example.gamercornerapp.data.NotificationItem

data class NotificationsState(
    val notifications: List<NotificationItem> = emptyList()
)