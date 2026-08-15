package com.example.gamercornerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gamercornerapp.ui.Screens.followers.FollowersScreen
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import com.example.gamercornerapp.ui.Screens.login.LoginScreen
import com.example.gamercornerapp.ui.Screens.notifications.NotificationsScreen
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.example.gamercornerapp.ui.Screens.register.RegisterScreen
import com.example.gamercornerapp.ui.Screens.review.ReviewScreen
import com.example.gamercornerapp.ui.Screens.startpage.StartApp
import com.example.gamercornerapp.ui.model.Game
import com.example.gamercornerapp.ui.model.NotificationItem
import com.example.gamercornerapp.ui.model.ReviewItem
import com.example.gamercornerapp.ui.model.UserProfile
import com.example.gamercornerapp.ui.model.UserStats

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamerCornerAppTheme {
                val game = Game(
                    title = "Cyberpunk 2026",
                    developer = "CD Projekt Red",
                    year = 2020,
                    image = R.drawable.cyberpunk
                )

                val userProfile = UserProfile (
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

                val reviewItem = listOf(
                    ReviewItem(
                        id = "1",
                        gameTitle = "Elden Ring",
                        rating = 5,
                        relativeDate = "Hace 2 días",
                        gameImageId = R.drawable.mini_elden,
                        description = "Un juego increible..."
                    ),
                    ReviewItem(
                        id = "2",
                        gameTitle = "Cyberpunk",
                        rating = 4,
                        relativeDate = "Hace 1 semana",
                        gameImageId = R.drawable.cyberpunk,
                        description = "Una gran historia..."
                    )
                )


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
                        userAvatarImage = R.drawable.messi2,
                        type = NotificationType.COMMENT,
                        actionText = "comentó en tu reseña de",
                        targetTitle = "Elden Ring",
                        commentQuote = "Excelente análisis! Totalmente de acuerdo contigo 🔥",
                        relativeTime = "Hace 1 hora"
                    ),
                    NotificationItem(
                        id = "3",
                        username = "ShadowX",
                        userAvatarImage = R.drawable.messi3,
                        type = NotificationType.LIKE,
                        actionText = "dio like a tu reseña de",
                        targetTitle = "God of War Ragnarök",
                        relativeTime = "Hace 2 horas"
                    ),
                    NotificationItem(
                        id = "4",
                        username = "NeoGamer",
                        userAvatarImage = R.drawable.messi4,
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
                    ),
                    NotificationItem(
                        id = "6",
                        username = "Drakool",
                        userAvatarImage = R.drawable.messi2,
                        type = NotificationType.FOLLOW,
                        actionText = "comenzó a seguirte.",
                        relativeTime = "Hace más de 5 horas"
                    ),
                    NotificationItem(
                        id = "7",
                        username = "LadyAki",
                        userAvatarImage = R.drawable.messi3,
                        type = NotificationType.COMMENT,
                        actionText = "comentó en tu reseña de",
                        targetTitle = "Elden Ring",
                        commentQuote = "Excelente análisis! Totalmente de acuerdo contigo 🔥",
                        relativeTime = "Hace más de 5 horas"
                    ),
                    NotificationItem(
                        id = "8",
                        username = "ShadowX",
                        userAvatarImage = R.drawable.messi4,
                        type = NotificationType.LIKE,
                        actionText = "dio like a tu reseña de",
                        targetTitle = "God of War Ragnarök",
                        relativeTime = "Hace más de 5 horas"
                    ),
                    NotificationItem(
                        id = "9",
                        username = "NeoGamer",
                        userAvatarImage = R.drawable.messi1,
                        type = NotificationType.COMMENT,
                        actionText = "comentó en tu reseña de",
                        targetTitle = "Cyberpunk 2077",
                        commentQuote = "A mí me encantó la historia, gran reseña bro!",
                        relativeTime = "Hace más de 5 horas"
                    ),
                    NotificationItem(
                        id = "10",
                        username = "PixelPro",
                        userAvatarImage = R.drawable.messi2,
                        type = NotificationType.LIKE,
                        actionText = "dio like a tu comentario en la reseña de",
                        targetTitle = "Baldur's Gate 3",
                        relativeTime = "Hace más de 2 días"
                    )
                )

                NotificationsScreen(sampleNotifications)
            }
        }
    }
}