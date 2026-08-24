package com.example.gamercornerapp.ui.Screens.videogame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameActionBar
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameCoverImage
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameHeaderInfo
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameRatingSection
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameSynopsis
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameTopBar
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.GameRatingBar
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameScreen(
    game: Game,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onWriteReviewClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    VideogameScreenContent(
        game = game,
        onBackClick = onBackClick,
        onShareClick = onShareClick,
        onSaveClick = onSaveClick,
        onWriteReviewClick = onWriteReviewClick,
        modifier = modifier
    )
}


@Composable
fun VideogameScreenContent(
    game: Game,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    onWriteReviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 16.dp
            )
            .padding(
                top = 12.dp,
                bottom = 24.dp
            )
    ) {

        VideogameTopBar(
            onBackClick = onBackClick,
            onShareClick = onShareClick
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        VideogameCoverImage(
            game = game
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        VideogameHeaderInfo(
            game = game
        )


        Spacer(
            modifier = Modifier.height(14.dp)
        )


        VideogameSynopsis(
            description = game.description
        )


        Spacer(
            modifier = Modifier.height(14.dp)
        )


        HorizontalDivider(
            color = MaterialTheme.colorScheme.surface
        )


        Spacer(
            modifier = Modifier.height(14.dp)
        )


        VideogameRatingSection(
            rating = game.rating,
            distribution = game.ratingDistribution
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        VideogameActionBar(
            onSaveClick = onSaveClick,
            onWriteReviewClick = onWriteReviewClick
        )
    }
}


@Preview(
    showBackground = true,
    name = "Elden Ring",
    widthDp = 412,
    heightDp = 892
)
@Composable
fun VideogameScreenPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        VideogameScreen(
            game = Game(
                title = "Elden Ring",
                developer = "FromSoftware",
                year = 2022,
                image = R.drawable.cyberpunk,
                rating = 4.8,
                reviewsCount = 1200,
                tags = listOf(
                    "RPG",
                    "Mundo Abierto",
                    "Fantasía"
                ),
                description = "Una obra maestra. Un vasto mundo lleno de secretos, jefes épicos y una historia profunda que te atrapa desde el primer momento.",
                ratingDistribution = listOf(
                    GameRatingBar(
                        stars = 5,
                        percentage = 0.9f
                    ),
                    GameRatingBar(
                        stars = 4,
                        percentage = 0.35f
                    ),
                    GameRatingBar(
                        stars = 3,
                        percentage = 0.15f
                    ),
                    GameRatingBar(
                        stars = 2,
                        percentage = 0.05f
                    ),
                    GameRatingBar(
                        stars = 1,
                        percentage = 0.05f
                    )
                )
            )
        )
    }
}