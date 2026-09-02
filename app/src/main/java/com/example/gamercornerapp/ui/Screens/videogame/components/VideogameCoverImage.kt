package com.example.gamercornerapp.ui.Screens.videogame.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameCoverImage(
    game: Game,
    modifier: Modifier = Modifier
) {

    val backgroundColor = MaterialTheme.colorScheme.background

    Card(
        modifier = modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        ) {

            Image(
                painter = painterResource(
                    id = game.image
                ),
                contentDescription = game.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )


            // Gradiente para que la transicion al fondo sea suave
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                backgroundColor.copy(alpha = 0.8f)
                            ),
                            startY = 400f
                        )
                    )
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Videogame Cover Dark"
)
@Composable
fun VideogameCoverImagePreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        ) {

            VideogameCoverImage(
                game = Game(
                    id = 3,
                    title = "Elden Ring",
                    developer = "FromSoftware",
                    year = 2022,
                    image = R.drawable.messi1
                )
            )
        }
    }
}