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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.review.components.GameInfoCard
import com.example.gamercornerapp.ui.Screens.review.components.OpinionSection
import com.example.gamercornerapp.ui.Screens.review.components.RatingSection
import com.example.gamercornerapp.ui.Screens.review.components.ReviewTitle
import com.example.gamercornerapp.ui.Screens.review.components.TagsSection
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ReviewScreen(
    game: Game,
    modifier: Modifier = Modifier
) {

    // Estado de la calificacion
    var rating by remember {
        mutableStateOf(5)
    }


    // Estado de la opinion
    var opinion by remember {
        mutableStateOf("")
    }


    ReviewScreenContent(
        game = game,

        rating = rating,
        opinion = opinion,

        onRatingChange = {
            rating = it
        },

        onOpinionChange = {
            opinion = it
        },

        onPublishClick = { },

        modifier = modifier
    )
}


@Composable
fun ReviewScreenContent(
    game: Game,

    rating: Int,
    opinion: String,

    onRatingChange: (Int) -> Unit,
    onOpinionChange: (String) -> Unit,

    onPublishClick: () -> Unit,

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


        TagsSection()


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

        ReviewScreen(
            game = Game(
                title = "Elden Ring",
                developer = "FromSoftware",
                year = 2022,
                image = R.drawable.messi
            )
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

        ReviewScreen(
            game = Game(
                title = "God of War",
                developer = "Santa Monica Studio",
                year = 2018,
                image = R.drawable.messi1
            )
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

        ReviewScreen(
            game = Game(
                title = "Minecraft",
                developer = "Mojang",
                year = 2011,
                image = R.drawable.messi2
            )
        )
    }
}