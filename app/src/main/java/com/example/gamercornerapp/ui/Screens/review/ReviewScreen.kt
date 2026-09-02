package com.example.gamercornerapp.ui.Screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.ui.Screens.review.components.GameInfoCard
import com.example.gamercornerapp.ui.Screens.review.components.OpinionSection
import com.example.gamercornerapp.ui.Screens.review.components.RatingSection
import com.example.gamercornerapp.ui.Screens.review.components.ReviewTitle
import com.example.gamercornerapp.ui.Screens.review.components.TagsSection
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ReviewScreen(
    gameId: Int,
    onPublishClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(gameId) {
        viewModel.loadGame(gameId)
    }

    val game = uiState.game

    if (game != null) {
        ReviewScreenContent(
            game = game,
            rating = uiState.rating,
            opinion = uiState.opinion,
            selectedTags = uiState.selectedTags,
            onRatingChange = viewModel::onRatingChange,
            onOpinionChange = viewModel::onOpinionChange,
            onTagToggle = viewModel::onTagToggle,
            onPublishClick = onPublishClick,
            modifier = modifier
        )
    } else {
        Text(text = stringResource(id = R.string.error_game_not_found))
    }
}


@Composable
fun ReviewScreenContent(
    game: Game,

    rating: Int,
    opinion: String,

    onRatingChange: (Int) -> Unit,
    onOpinionChange: (String) -> Unit,

    onPublishClick: () -> Unit,

    modifier: Modifier = Modifier,
    selectedTags: Set<String> = emptySet(),
    onTagToggle: (String) -> Unit = {}
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
            .padding(16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(8.dp)
        )


        ReviewTitle()


        GameInfoCard(
            game = game
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        RatingSection(
            rating = rating,
            onRatingChange = onRatingChange
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        OpinionSection(
            opinion = opinion,
            onOpinionChange = onOpinionChange
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        TagsSection(
            selectedTags = selectedTags,
            onTagToggle = onTagToggle
        )


        Spacer(
            modifier = Modifier.height(145.dp)
        )


        AppButton(
            text = stringResource(
                id = R.string.btn_publish_review
            ),
            onClick = onPublishClick
        )
    }
}


@Preview(
    showBackground = true,
    name = "Elden Ring"
)
@Composable
fun ReviewScreenEldenRingPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        ReviewScreenContent(
            game = Game(
                id = 1,
                title = "Elden Ring",
                developer = "FromSoftware",
                year = 2022,
                image = R.drawable.elden
            ),

            rating = 5,
            opinion = "",
            selectedTags = setOf("Historia"),
            onRatingChange = {},
            onOpinionChange = {},
            onTagToggle = {},
            onPublishClick = { }
        )
    }
}


@Preview(
    showBackground = true,
    name = "God of War"
)
@Composable
fun ReviewScreenGodOfWarPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        ReviewScreenContent(
            game = Game(
                id = 2,
                title = "God of War",
                developer = "Santa Monica Studio",
                year = 2018,
                image = R.drawable.cyberpunk
            ),

            rating = 5,
            opinion = "",
            onRatingChange = {},
            onOpinionChange = {},
            onTagToggle = {},
            onPublishClick = { }
        )
    }
}


@Preview(
    showBackground = true,
    name = "Minecraft"
)
@Composable
fun ReviewScreenMinecraftPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        ReviewScreenContent(
            game = Game(
                id = 6,
                title = "Minecraft",
                developer = "Mojang",
                year = 2011,
                image = R.drawable.hog
            ),

            rating = 5,
            opinion = "",
            onRatingChange = {},
            onOpinionChange = {},
            onTagToggle = {},
            onPublishClick = { }
        )
    }
}