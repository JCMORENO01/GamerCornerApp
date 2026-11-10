
package com.example.gamercornerapp.ui.Screens.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gamercornerapp.data.Note
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun NoteBubble(
    note: Note?,
    isOwnNote: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(100.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(
            modifier = Modifier
                .width(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = note?.text ?: "Crear nota +",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(14.dp)
                    )
                    .then(
                        Modifier
                            .width(100.dp)
                    )
            )
        }

        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primaryContainer
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            if (!note?.profilePictureUrl.isNullOrBlank()) {

                AsyncImage(
                    model = note?.profilePictureUrl,
                    contentDescription = "Foto de ${note?.username}",
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )

            } else if (isOwnNote && note == null) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Crear nota",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )

            } else {

                Text(
                    text = note?.username
                        ?.take(1)
                        ?.uppercase()
                        ?: "G",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Text(
            text = if (isOwnNote) {
                "Tu nota"
            } else {
                note?.username ?: "Gamer"
            },
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoteBubblePreview() {
    GamerCornerAppTheme(darkTheme = true) {
        NoteBubble(
            note = Note(
                id = "1",
                userId = "1",
                username = "Gamer01",
                profilePictureUrl = null,
                text = "¿Quién juega Valorant?",
                createdAtMillis = 0L,
                expiresAtMillis = 0L
            ),
            isOwnNote = false,
            onClick = {}
        )
    }
}
