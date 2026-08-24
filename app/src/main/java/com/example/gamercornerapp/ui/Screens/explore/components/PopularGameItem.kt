package com.example.gamercornerapp.ui.Screens.explore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.Game
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import com.example.gamercornerapp.ui.util.formatReviewsCount


@Composable
fun PopularGameItem(
    rank: Int,
    game: Game,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            // Numero de ranking
            Text(
                text = rank.toString(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(24.dp)
            )


            Spacer(
                modifier = Modifier.width(8.dp)
            )


            // Portada del juego
            Image(
                painter = painterResource(
                    id = game.image
                ),

                contentDescription =
                    "Portada de ${game.title}",

                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .size(
                        width = 48.dp,
                        height = 60.dp
                    )
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.background
                    )
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            // Titulo y calificacion
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = game.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )


                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(14.dp)
                    )


                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )


                    Text(
                        text = "%.1f".format(game.rating),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )


                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )


                    Text(
                        text = stringResource(
                            id = R.string.reviews_count_parentheses,
                            formatReviewsCount(
                                game.reviewsCount
                            )
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }


            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Popular Game Item Dark"
)
@Composable
fun PopularGameItemPreview() {

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

            PopularGameItem(
                rank = 1,

                game = Game(
                    title = "Hogwarts Legacy",
                    developer = "Avalanche Software",
                    year = 2023,
                    image = R.drawable.elden,
                    rating = 4.7,
                    reviewsCount = 2100
                )
            )
        }
    }
}
