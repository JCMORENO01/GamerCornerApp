package com.example.gamercornerapp.ui.Screens.notifications.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.example.gamercornerapp.R

@Composable
fun UserAvatarWithBadge(
    avatarRes: Int,
    type: NotificationType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(50.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(id = avatarRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
        )

        //icono del tipo de notificación
        val badgeBg = when (type) {
            NotificationType.FOLLOW -> colorResource(id = R.color.brand_purple)
            NotificationType.COMMENT -> colorResource(id = R.color.brand_primary)
            NotificationType.LIKE -> Color(0xFFEF4444)
        }

        val badgeIcon = when (type) {
            NotificationType.FOLLOW -> Icons.Default.Person
            NotificationType.COMMENT -> Icons.Default.ChatBubble
            NotificationType.LIKE -> Icons.Default.Favorite
        }

        Box(
            modifier = Modifier
                .size(18.dp)
                .background(badgeBg, CircleShape)
                .border(1.5.dp, colorResource(id = R.color.card_background), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = badgeIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(10.dp)
            )
        }
    }
}





@Preview()
@Composable
fun UserAvatarWithBadgePreview (){
    UserAvatarWithBadge(R.drawable.messi2, NotificationType.LIKE)
}
