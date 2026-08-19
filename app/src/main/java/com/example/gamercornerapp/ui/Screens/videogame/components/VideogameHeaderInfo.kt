package com.example.gamercornerapp.ui.Screens.videogame.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.Game
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import kotlin.math.roundToInt


@Composable
fun VideogameHeaderInfo(
    game: Game,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = game.title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(4.dp)
        )


        Text(
            text = "${game.developer} • ${game.year}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "%.1f".format(game.rating),
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.width(8.dp)
            )


            val fullStars =
                game.rating.roundToInt().coerceIn(0, 5)


            Row {

                repeat(5) { index ->

                    Icon(
                        imageVector = if (index < fullStars) {
                            Icons.Filled.Star
                        } else {
                            Icons.Filled.StarBorder
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }


            Spacer(
                modifier = Modifier.width(8.dp)
            )


            Text(
                text = stringResource(
                    id = R.string.reviews_count_format,
                    formatReviewsCount(game.reviewsCount)
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }


        if (game.tags.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(14.dp)
            )


            VideogameTagsRow(
                tags = game.tags
            )
        }
    }
}


// Convierte 1200 en "1.2k"
private fun formatReviewsCount(
    count: Int
): String {

    return if (count >= 1000) {
        "%.1fk".format(
            count / 1000f
        )
    } else {
        count.toString()
    }
}


@Preview(
    showBackground = true,
    name = "Videogame Header Dark"
)
@Composable
fun VideogameHeaderInfoPreview() {

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

            VideogameHeaderInfo(
                game = Game(
                    title = "Elden Ring",
                    developer = "FromSoftware",
                    year = 2022,
                    image = R.drawable.mini_elden,
                    rating = 4.8,
                    reviewsCount = 1200,
                    tags = listOf(
                        "RPG",
                        "Mundo Abierto",
                        "Fantasía"
                    )
                )
            )
        }
    }
}