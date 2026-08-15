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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.example.gamercornerapp.ui.model.NotificationItem
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun NotificationCardItem(
    notification: NotificationItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_background)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar con badge flotante
            UserAvatarWithBadge(
                avatarRes = notification.userAvatarImage,
                type = notification.type
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Texto de la notificación
            Column(modifier = Modifier.weight(1f)) {
                val annotatedText = buildAnnotatedString {
                    //nombre de usuario en negrita
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                        append(notification.username)
                    }
                    append(" ")
                    append(notification.actionText)

                    //solo si es de un juego se agrega su nombre
                    notification.targetTitle?.let { title ->
                        append(" ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                            append(title)
                        }
                    }
                    append(".")
                }

                Text(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.white),
                    fontSize = 13.sp
                )

                // Cita del comentario (si aplica)
                notification.commentQuote?.let { quote ->
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "\"$quote\"",
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = notification.relativeTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = 11.sp
                )
            }
        }
    }
}




@Preview()
@Composable
fun NotificationCardItemPreview (){
    GamerCornerAppTheme (){
        NotificationCardItem(
            NotificationItem(
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