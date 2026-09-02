package com.example.gamercornerapp.ui.Screens.selfProfile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.UserStats
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ProfileHeaderSection(
    userProfile: UserProfile,
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Image(
                painter = painterResource(
                    id = userProfile.profileBackgroundId
                ),
                contentDescription = userProfile.profileBgDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(
                        RoundedCornerShape(24.dp)
                    )
            )


            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(36.dp)
            ) {

                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(id = R.string.cd_settings),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }


            // Foto de perfil
            Image(
                painter = painterResource(
                    id = userProfile.profileImageId
                ),
                contentDescription = stringResource(
                    id = R.string.user_profile_photo_description,
                    userProfile.username
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .offset(
                        y = 45.dp
                    )
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
            )
        }


        Spacer(
            modifier = Modifier.height(52.dp)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = userProfile.username,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }


        Spacer(
            modifier = Modifier.height(2.dp)
        )


        // Nickname
        Text(
            text = userProfile.nickName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Bio
        Text(
            text = userProfile.bio,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(
                horizontal = 24.dp
            )
        )
    }
}


@Preview(
    showBackground = true,
    name = "Profile Header Dark"
)
@Composable
fun ProfileHeaderSectionPreview() {

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

            ProfileHeaderSection(
                userProfile = UserProfile(
                    username = "NightHunter",
                    nickName = "@nighthunter_21",
                    bio = "Vivo para los videojuegos 🎮",
                    profileBackgroundId = R.drawable.background_maquinitas,
                    profileBgDescription = "Imagen de monitores",
                    profileImageId = R.drawable.messi1,
                    stats = UserStats(
                        reviewsCount = 128,
                        followersCount = 342,
                        followingCount = 176
                    )
                )
            )
        }
    }
}