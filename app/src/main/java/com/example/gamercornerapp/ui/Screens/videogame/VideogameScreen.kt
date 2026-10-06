package com.example.gamercornerapp.ui.Screens.videogame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.GameRatingBar
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameActionBar
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameCommentsSection
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameCoverImage
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameHeaderInfo
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameRatingSection
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameSynopsis
import com.example.gamercornerapp.ui.Screens.videogame.components.VideogameTopBar
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun VideogameScreen(
    gameId: Int,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onWriteReviewClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: VideogameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(gameId) {
        viewModel.loadGame(gameId)
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (uiState.game != null) {
        VideogameScreenContent(
            game = uiState.game!!,
            reviews = uiState.reviews,
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            onSaveClick = onSaveClick,
            onWriteReviewClick = onWriteReviewClick,
            modifier = modifier
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.error_game_not_found),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun VideogameScreenContent(
    game: Game,
    reviews: List<ReviewItem>,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    onWriteReviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp)
        ) {
            VideogameCoverImage(game = game)

            VideogameTopBar(
                onBackClick = onBackClick,
                onShareClick = onShareClick,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            VideogameHeaderInfo(game = game)

            Spacer(modifier = Modifier.height(24.dp))

            VideogameSynopsis(description = game.description)

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            VideogameRatingSection(
                rating = game.rating,
                distribution = game.ratingDistribution
            )

            Spacer(modifier = Modifier.height(30.dp))

            VideogameActionBar(
                onSaveClick = onSaveClick,
                onWriteReviewClick = onWriteReviewClick
            )

            Spacer(modifier = Modifier.height(30.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(20.dp))

            VideogameCommentsSection(reviews = reviews)
        }
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
    GamerCornerAppTheme(darkTheme = true) {
        VideogameScreenContent(
            game = Game(
                id = 1,
                title = "Elden Ring",
                developer = "FromSoftware",
                year = 2022,
                image = R.drawable.cyberpunk,
                rating = 4.8,
                reviewsCount = 1200,
                tags = listOf("RPG", "Mundo Abierto", "Fantasía"),
                description = "Una obra maestra.",
                ratingDistribution = listOf(
                    GameRatingBar(stars = 5, percentage = 0.9f)
                )
            ),
            reviews = listOf(
                ReviewItem(
                    id = "1",
                    gameTitle = "Elden Ring",
                    rating = 5,
                    relativeDate = "Hace 2 horas",
                    gameImageId = R.drawable.mini_elden,
                    description = "¡Excelente juego, totalmente recomendado!"
                )
            ),
            onBackClick = {},
            onShareClick = {},
            onSaveClick = {},
            onWriteReviewClick = {}
        )
    }
}
