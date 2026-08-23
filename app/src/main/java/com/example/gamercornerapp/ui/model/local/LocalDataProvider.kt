package com.example.gamercornerapp.ui.model.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsSoccer
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.FeedPost
import com.example.gamercornerapp.ui.model.Game
import com.example.gamercornerapp.ui.model.GameCategory
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


    // Categorias/generos disponibles para filtrar en Explorar
    val exploreCategories = listOf(

        GameCategory(
            name = "Todos",
            icon = Icons.Default.AutoAwesome
        ),

        GameCategory(
            name = "Acción",
            icon = Icons.Default.Bolt
        ),

        GameCategory(
            name = "Aventura",
            icon = Icons.Default.Explore
        ),

        GameCategory(
            name = "RPG",
            icon = Icons.Default.Shield
        ),

        GameCategory(
            name = "Deportes",
            icon = Icons.Default.SportsSoccer
        ),

        GameCategory(
            name = "Arcade",
            icon = Icons.Default.SportsEsports
        )
    )


    // Juegos populares de la semana (lista con ranking)
    val popularGames = listOf(

        Game(
            title = "Hogwarts Legacy",
            developer = "Avalanche Software",
            year = 2023,
            image = R.drawable.hog,
            rating = 4.7,
            reviewsCount = 2100
        ),

        Game(
            title = "Baldur's Gate 3",
            developer = "Larian Studios",
            year = 2023,
            image = R.drawable.bal,
            rating = 4.9,
            reviewsCount = 3400
        ),

        Game(
            title = "Cyberpunk 2077",
            developer = "CD Projekt Red",
            year = 2020,
            image = R.drawable.cyberpunk,
            rating = 4.2,
            reviewsCount = 1800
        )
    )


    // Resultados generales mostrados en la cuadricula de Explorar
    val exploreResults = listOf(

        Game(
            title = "Elden Ring",
            developer = "FromSoftware",
            year = 2022,
            image = R.drawable.elden,
            rating = 4.8,
            reviewsCount = 5200
        ),

        Game(
            title = "Baldur's Gate 3",
            developer = "Larian Studios",
            year = 2023,
            image = R.drawable.bal,
            rating = 4.9,
            reviewsCount = 3400
        ),

        Game(
            title = "Hogwarts Legacy",
            developer = "Avalanche Software",
            year = 2023,
            image = R.drawable.img,
            rating = 4.7,
            reviewsCount = 2100
        ),

        Game(
            title = "God of War",
            developer = "Santa Monica Studio",
            year = 2022,
            image = R.drawable.cyberpunk,
            rating = 4.7,
            reviewsCount = 1980
        )
    )
}