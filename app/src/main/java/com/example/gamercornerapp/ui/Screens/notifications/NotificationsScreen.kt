package com.example.gamercornerapp.ui.Screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.notifications.components.NotificationCardItem
import com.example.gamercornerapp.ui.Screens.notifications.components.NotificationsHeader
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.example.gamercornerapp.ui.model.NotificationItem
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun NotificationsScreen(
    notifications: List<NotificationItem>,
    modifier: Modifier = Modifier
) {

    NotificationScreenContent(
        notifications = notifications,
        modifier = modifier
    )
}


@Composable
fun NotificationScreenContent(
    notifications: List<NotificationItem>,
    modifier: Modifier = Modifier,
    onNotificationClick: (NotificationItem) -> Unit = { }
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        NotificationsHeader()


        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),

            verticalArrangement = Arrangement.spacedBy(12.dp),

            modifier = Modifier.fillMaxSize()
        ) {

            items(
                items = notifications,
                key = { it.id }
            ) { notification ->

                NotificationCardItem(
                    notification = notification,
                    onClick = {
                        onNotificationClick(notification)
                    }
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Notifications Dark"
)
@Composable
fun NotificationsScreenPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        val sampleNotifications = listOf(

            NotificationItem(
                id = "1",
                username = "Drakool",
                userAvatarImage = R.drawable.messi1,
                type = NotificationType.FOLLOW,
                actionText = "comenzó a seguirte.",
                relativeTime = "Hace 5 min"
            ),

            NotificationItem(
                id = "2",
                username = "LadyAki",
                userAvatarImage = R.drawable.messi1,
                type = NotificationType.COMMENT,
                actionText = "comentó en tu reseña de",
                targetTitle = "Elden Ring",
                commentQuote = "Excelente análisis! Totalmente de acuerdo contigo 🔥",
                relativeTime = "Hace 1 hora"
            ),

            NotificationItem(
                id = "3",
                username = "ShadowX",
                userAvatarImage = R.drawable.messi1,
                type = NotificationType.LIKE,
                actionText = "dio like a tu reseña de",
                targetTitle = "God of War Ragnarök",
                relativeTime = "Hace 2 horas"
            ),

            NotificationItem(
                id = "4",
                username = "NeoGamer",
                userAvatarImage = R.drawable.messi1,
                type = NotificationType.COMMENT,
                actionText = "comentó en tu reseña de",
                targetTitle = "Cyberpunk 2077",
                commentQuote = "A mí me encantó la historia, gran reseña bro!",
                relativeTime = "Hace 3 horas"
            ),

            NotificationItem(
                id = "5",
                username = "PixelPro",
                userAvatarImage = R.drawable.messi1,
                type = NotificationType.LIKE,
                actionText = "dio like a tu comentario en la reseña de",
                targetTitle = "Baldur's Gate 3",
                relativeTime = "Hace 4 horas"
            )
        )


        NotificationsScreen(
            notifications = sampleNotifications
        )
    }
}