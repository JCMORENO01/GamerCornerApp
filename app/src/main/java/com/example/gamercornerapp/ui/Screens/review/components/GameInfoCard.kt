package com.example.gamercornerapp.ui.Screens.review.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun GameInfoCard(
    game: Game,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = game.image
                ),
                contentDescription = game.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(
                        width = 84.dp,
                        height = 110.dp
                    )
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
            )


            Spacer(
                modifier = Modifier.width(16.dp)
            )


            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = game.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )


                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                Text(
                    text = "${game.developer} • ${game.year}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Game Info Card"
)
@Composable
fun GameInfoCardPreview() {

    val game = Game(
        id = 4,
        title = "Elden Ring",
        developer = "FromSoftware",
        year = 2022,
        image = R.drawable.elden
    )


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

            GameInfoCard(
                game = game
            )
        }
    }
}