package com.example.gamercornerapp.ui.Screens.notifications

import com.example.gamercornerapp.data.NotificationItem

enum class NotificationFilter(val displayName: String) {
    ALL("Todas"),
    LIKES("Likes"),
    FOLLOWERS("Seguidores"),
    COMMENTS("Comentarios")
}

data class NotificationsState(
    val allNotifications: List<NotificationItem> = emptyList(),
    val notifications: List<NotificationItem> = emptyList(),
    val selectedFilter: NotificationFilter = NotificationFilter.ALL,
    val isDropdownExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
