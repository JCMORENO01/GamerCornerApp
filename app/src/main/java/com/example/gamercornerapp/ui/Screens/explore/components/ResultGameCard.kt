package com.example.gamercornerapp.ui.Screens.explore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ResultGameCard(
    game: Game,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    onFavoriteClick: () -> Unit = { }
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

        Column {

            Box {

                Image(
                    painter = painterResource(
                        id = game.image
                    ),

                    contentDescription =
                        "Portada de ${game.title}",

                    contentScale = ContentScale.Crop,

                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.8f)
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp
                            )
                        )
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                )


                // Insignia de calificacion
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    modifier = Modifier
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(
                            MaterialTheme.colorScheme.background
                                .copy(alpha = 0.75f)
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                ) {

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(12.dp)
                    )


                    Spacer(
                        modifier = Modifier.size(4.dp)
                    )


                    Text(
                        text = "%.1f".format(game.rating),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.Top
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = game.title,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )


                        Text(
                            text = game.developer,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }


                    IconButton(
                        onClick = onFavoriteClick,

                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                MaterialTheme.colorScheme.primary
                                    .copy(alpha = if (isFavorite) 1f else 0.15f)
                            )
                    ) {

                        Icon(
                            imageVector = if (isFavorite) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },

                            contentDescription = stringResource(
                                id = R.string.content_description_favorite
                            ),

                            tint = if (isFavorite) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.primary
                            },

                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Result Game Card Dark"
)
@Composable
fun ResultGameCardPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
                .size(
                    width = 170.dp,
                    height = 260.dp
                )
        ) {

            ResultGameCard(
                game = Game(
                    id = 3,
                    title = "Baldur's Gate 3",
                    developer = "Larian Studios",
                    year = 2023,
                    image = R.drawable.elden,
                    rating = 4.9,
                    reviewsCount = 3400
                ),
                isFavorite = true
            )
        }
    }
}
