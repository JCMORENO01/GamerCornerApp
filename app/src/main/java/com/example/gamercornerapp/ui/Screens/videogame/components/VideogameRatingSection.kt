package com.example.gamercornerapp.ui.Screens.videogame.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.GameRatingBar
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameRatingSection(
    rating: Double,
    distribution: List<GameRatingBar>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(
                id = R.string.label_community_rating
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "%.1f".format(rating),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(80.dp)
            )


            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                distribution.forEach { bar ->

                    RatingBarRow(
                        bar = bar
                    )
                }
            }
        }
    }
}


@Composable
private fun RatingBarRow(
    bar: GameRatingBar
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "${bar.stars}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
            modifier = Modifier.width(14.dp)
        )


        Spacer(
            modifier = Modifier.width(8.dp)
        )


        Row(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(
                    RoundedCornerShape(4.dp)
                )
                .background(
                    MaterialTheme.colorScheme.surface
                )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(
                        bar.percentage.coerceIn(
                            0f,
                            1f
                        )
                    )
                    .height(8.dp)
                    .clip(
                        RoundedCornerShape(4.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
            ) { }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Videogame Rating Dark"
)
@Composable
fun VideogameRatingSectionPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        VideogameRatingSection(
            rating = 4.8,

            distribution = listOf(
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
            ),

            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        )
    }
}