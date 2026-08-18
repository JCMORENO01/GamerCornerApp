package com.example.gamercornerapp.ui.Screens.videogame.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.Game
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameCoverImage(
    game: Game,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_background)
        )
    ) {

        Image(
            painter = painterResource(id = game.image),
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun VideogameCoverImagePreview() {

    GamerCornerAppTheme {

        VideogameCoverImage(
            game = Game(
                title = "Elden Ring",
                developer = "FromSoftware",
                year = 2022,
                image = R.drawable.messi1
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}