package com.example.gamercornerapp.ui.Screens.selfProfile.components

import android.net.Uri
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
import androidx.compose.material.icons.automirrored.outlined.Logout
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
import com.example.gamercornerapp.ui.componentes.ProfileAsyncImage
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable


@Composable
fun ProfileHeaderSection(
    userProfile: UserProfile,
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onImageSelected: (Uri) -> Unit = {}
) {
    //launcher para seleccionar img de la galeria
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { onImageSelected(it) } //si se selecciona una foto se dispara la acción
        }
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Image(
                painter = painterResource(id = userProfile.profileBackgroundId),
                contentDescription = userProfile.profileBgDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            IconButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        shape = CircleShape
                    )
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = MaterialTheme.colorScheme.error
                )
            }


            ProfileAsyncImage(
                profileImage = userProfile.profilePictureUrl ?: userProfile.profileImageId,
                size = 110.dp,
                modifier = Modifier
                    .offset(y = 45.dp)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
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