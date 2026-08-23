package com.example.gamercornerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.gamercornerapp.ui.Screens.explore.ExploreScreen
import com.example.gamercornerapp.ui.Screens.feed.FeedScreen
import com.example.gamercornerapp.ui.Screens.followers.FollowersScreen
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import com.example.gamercornerapp.ui.Screens.login.LoginScreen
import com.example.gamercornerapp.ui.Screens.notifications.NotificationsScreen
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import com.example.gamercornerapp.ui.Screens.register.RegisterScreen
import com.example.gamercornerapp.ui.Screens.review.ReviewScreen
import com.example.gamercornerapp.ui.Screens.selfProfile.SelfProfileScreen
import com.example.gamercornerapp.ui.Screens.startpage.StartApp
import com.example.gamercornerapp.ui.Screens.videogame.VideogameScreen
import com.example.gamercornerapp.ui.model.FeedPost
import com.example.gamercornerapp.ui.model.Game
import com.example.gamercornerapp.ui.model.NotificationItem
import com.example.gamercornerapp.ui.model.local.LocalDataProvider
import com.example.gamercornerapp.ui.model.ReviewItem
import com.example.gamercornerapp.ui.model.UserProfile
import com.example.gamercornerapp.ui.model.UserStats
import com.example.gamercornerapp.ui.componentes.GamerBottomBar

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

                val katanaGamerProfile = UserProfile(
                    username = "KatanaGamer",
                    nickName = "@katana_gamer",
                    bio = "Fan de los souls-like 🗡️",
                    profileBackgroundId = R.drawable.background_maquinitas,
                    profileBgDescription = "Imagen de monitores",
                    profileImageId = R.drawable.messi2,
                    stats = UserStats(
                        reviewsCount = 64,
                        followersCount = 210,
                        followingCount = 98
                    )
                )

                val samplePosts = listOf(
                    FeedPost(
                        id = "1",
                        author = userProfile,
                        relativeTime = "Hace 2 horas",
                        game = Game(
                            title = "Elden Ring",
                            developer = "FromSoftware",
                            year = 2022,
                            image = R.drawable.mini_elden
                        ),
                        rating = 4.8,
                        description = "Simplemente una obra maestra. El mundo, la historia, los jefes... Todo aquí te reta y te recompensa. Inolvidable.",
                        tags = listOf("RPG", "Mundo Abierto"),
                        likesCount = 256,
                        commentsCount = 42
                    ),
                    FeedPost(
                        id = "2",
                        author = katanaGamerProfile,
                        relativeTime = "Hace 4 horas",
                        game = Game(
                            title = "God of War",
                            developer = "Santa Monica Studio",
                            year = 2022,
                            image = R.drawable.cyberpunk
                        ),
                        rating = 4.7,
                        description = "Una secuela épica. Combate brutal, historia increíble y personajes que te marcan.",
                        tags = listOf("Acción", "Aventura"),
                        likesCount = 198,
                        commentsCount = 31
                    )
                )

                var selectedTab by remember { mutableIntStateOf(0) }

                Scaffold(
                    bottomBar = {
                        GamerBottomBar(
                            selectedTab = selectedTab,
                            onTabSelected = { selectedTab = it }
                        )
                    }
                ) { innerPadding ->
                    when (selectedTab) {
                        0 -> FeedScreen(
                            posts = samplePosts,
                            modifier = Modifier.padding(innerPadding)
                        )
                        1 -> ExploreScreen(
                            popularGames = LocalDataProvider.popularGames,
                            categories = LocalDataProvider.exploreCategories,
                            resultGames = LocalDataProvider.exploreResults,
                            modifier = Modifier.padding(innerPadding)
                        )
                        2 -> NotificationsScreen(
                            notifications = sampleNotifications,
                            modifier = Modifier.padding(innerPadding)
                        )
                        3 -> SelfProfileScreen(
                            userProfile = userProfile,
                            reviews = reviewItem,
                            modifier = Modifier.padding(innerPadding)
                        )
                        else -> FeedScreen(
                            posts = samplePosts,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}