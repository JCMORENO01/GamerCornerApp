package com.example.gamercornerapp.ui.Screens.followers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.FollowerItem
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun UserRow(
    user: FollowerItem,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Imagen e informacion del usuario
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = user.avatarImage
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Column {

                Text(
                    text = user.username,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = user.handle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        }


        // Boton seguir
        Box(
            modifier = Modifier
                .background(
                    color = if (user.isFollowing) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        MaterialTheme.colorScheme.primary
                    },

                    shape = RoundedCornerShape(20.dp)
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 8.dp
                ),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = if (user.isFollowing) {

                    stringResource(
                        id = R.string.btn_following
                    )

                } else {

                    stringResource(
                        id = R.string.btn_follow
                    )
                },

                color = if (user.isFollowing) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onPrimary
                },

                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "No siguiendo"
)
@Composable
fun UserRowNotFollowingPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        ) {

            UserRow(
                user = FollowerItem(
                    id = "1",
                    username = "Drakool",
                    handle = "@drakool",
                    avatarImage = R.drawable.messi1,
                    isFollowing = false
                )
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Siguiendo"
)
@Composable
fun UserRowFollowingPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        ) {

            UserRow(
                user = FollowerItem(
                    id = "2",
                    username = "LadyAki",
                    handle = "@ladyaki",
                    avatarImage = R.drawable.messi2,
                    isFollowing = true
                )
            )
        }
    }
}