package com.example.gamercornerapp.ui.model.local

import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.FeedPost
import com.example.gamercornerapp.ui.model.Game
import com.example.gamercornerapp.ui.model.UserProfile
import com.example.gamercornerapp.ui.model.UserStats


object LocalDataProvider {

    val posts = listOf(

        FeedPost(
            id = "1",

            author = UserProfile(
                username = "NightHunter",
                nickName = "@nighthunter_21",
                bio = "Vivo para los videojuegos",
                profileBackgroundId = R.drawable.background_maquinitas,
                profileBgDescription = "Imagen de monitores",
                profileImageId = R.drawable.messi1,

                stats = UserStats(
                    reviewsCount = 128,
                    followersCount = 342,
                    followingCount = 176
                )
            ),

            relativeTime = "Hace 2 horas",

            game = Game(
                title = "Elden Ring",
                developer = "FromSoftware",
                year = 2022,
                image = R.drawable.mini_elden
            ),

            rating = 4.8,

            description = "Simplemente una obra maestra. El mundo, la historia, los jefes... Todo aquí te reta y te recompensa. Inolvidable.",

            tags = listOf(
                "RPG",
                "Mundo Abierto"
            ),

            likesCount = 256,
            commentsCount = 42
        ),


        FeedPost(
            id = "2",

            author = UserProfile(
                username = "KatanaGamer",
                nickName = "@katana_gamer",
                bio = "Fan de los souls-like",
                profileBackgroundId = R.drawable.background_maquinitas,
                profileBgDescription = "Imagen de monitores",
                profileImageId = R.drawable.messi2,

                stats = UserStats(
                    reviewsCount = 64,
                    followersCount = 210,
                    followingCount = 98
                )
            ),

            relativeTime = "Hace 4 horas",

            game = Game(
                title = "God of War",
                developer = "Santa Monica Studio",
                year = 2022,
                image = R.drawable.cyberpunk
            ),

            rating = 4.7,

            description = "Una secuela épica. Combate brutal, historia increíble y personajes que te marcan.",

            tags = listOf(
                "Acción",
                "Aventura"
            ),

            likesCount = 198,
            commentsCount = 31
        )
    )
}