package com.example.gamercornerapp.ui.Screens.notifications.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.example.gamercornerapp.data.NotificationItem
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun NotificationCardItem(
    notification: NotificationItem,
    onClick: () -> Unit = { },
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),

            verticalAlignment = Alignment.Top
        ) {

            // Avatar con badge
            UserAvatarWithBadge(
                avatarRes = notification.userAvatarImage,
                type = notification.type
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Column(
                modifier = Modifier.weight(1f)
            ) {

                val target = notification.targetTitle?.let { " $it." } ?: "."
                val fullText = "${notification.username} ${notification.actionText}$target"

                Text(
                    text = fullText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp
                )


                // Cita del comentario
                notification.commentQuote?.let { quote ->

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )


                    Text(
                        text = "\"$quote\"",
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }


                Spacer(
                    modifier = Modifier.height(6.dp)
                )


                Text(
                    text = notification.relativeTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NotificationCardItemPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        NotificationCardItem(
            notification = NotificationItem(
                id = "1",
                username = "Drakool",
                userAvatarImage = R.drawable.messi1,
                type = NotificationType.FOLLOW,
                actionText = "comenzó a seguirte.",
                relativeTime = "Hace 5 min"
            )
        )
    }
}