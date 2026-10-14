
package com.example.gamercornerapp.ui.Screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.NotificationItem
import com.example.gamercornerapp.ui.Screens.notifications.components.NotificationCardItem
import com.example.gamercornerapp.ui.Screens.notifications.components.NotificationsHeader
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NotificationScreenContent(
        notifications = uiState.notifications,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        modifier = modifier
    )
}

@Composable
fun NotificationScreenContent(
    notifications: List<NotificationItem>,
    modifier: Modifier = Modifier,
    onNotificationClick: (NotificationItem) -> Unit = {},
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        NotificationsHeader()

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }

        } else if (notifications.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Todavía no tienes notificaciones",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        } else {
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
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        NotificationScreenContent(
            notifications = listOf(
                NotificationItem(
                    id = "1",
                    username = "PixelGamer",
                    userAvatarImage = R.drawable.messi1,
                    type = NotificationType.LIKE,
                    actionText = "dio like a tu reseña de",
                    targetTitle = "Elden Ring",
                    relativeTime = "Hace 5 min"
                )
            )
        )
    }
}
