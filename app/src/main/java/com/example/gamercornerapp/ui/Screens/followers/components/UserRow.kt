
package com.example.gamercornerapp.ui.Screens.followers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gamercornerapp.data.FollowerItem

@Composable
fun UserRow(
    user: FollowerItem,
    modifier: Modifier = Modifier,
    onUserClick: () -> Unit = {},
    onFollowClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable { onUserClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!user.avatarUrl.isNullOrBlank()) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = "Foto de ${user.username}",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(user.avatarImage),
                    error = painterResource(user.avatarImage),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(user.avatarImage),
                    contentDescription = "Foto de ${user.username}",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = user.username,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = user.handle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (user.isFollowing) {
            OutlinedButton(onClick = onFollowClick) {
                Text("Siguiendo")
            }
        } else {
            Button(onClick = onFollowClick) {
                Text("Seguir")
            }
        }
    }
}
