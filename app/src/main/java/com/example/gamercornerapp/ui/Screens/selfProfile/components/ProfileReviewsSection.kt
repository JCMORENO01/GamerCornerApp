package com.example.gamercornerapp.ui.Screens.selfProfile.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.ReviewItem
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun ProfileReviewsSection(
    reviews: List<ReviewItem>,
    modifier: Modifier = Modifier,
    onSeeAllClick: () -> Unit = {},
    onReviewClick: (ReviewItem) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Reseñas", "Guardados")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.brand_primary),
            divider = {},
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTabIndex),
                    height = 3.dp,
                    color = colorResource(id = R.color.brand_primary)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index
                val textColor by animateColorAsState(
                    targetValue = if (isSelected) colorResource(id = R.color.brand_primary)
                    else colorResource(id = R.color.text_secondary),
                    label = "tabTextColor"
                )

                Tab(
                    selected = isSelected,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.background(
                        //TODO- Revisar colores
                        if (isSelected) colorResource(id = R.color.brand_primary).copy(alpha = 0.15f)
                        else Color.Transparent
                    ),
                    text = {
                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = textColor
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedTabIndex == 0) "RESEÑAS RECIENTES" else "GUARDADOS RECIENTES",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white),
                letterSpacing = 0.8.sp,
                fontSize = 12.sp
            )

            Text(
                text = "Ver todas",
                style = MaterialTheme.typography.labelMedium,
                color = colorResource(id = R.color.text_secondary),
                fontSize = 12.sp,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        //por cada review aparecerá una tarjetica
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            reviews.forEach { review ->
                ReviewCardItem(
                    review = review,
                    onClick = { onReviewClick(review) }
                )
            }
        }
    }
}

@Composable
private fun ReviewCardItem(
    review: ReviewItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_background)
        ),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = review.gameImageId),
                contentDescription = "Portada de ${review.gameTitle}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 60.dp, height = 64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(R.color.brand_background))
            )

            Spacer(modifier = Modifier.width(14.dp))

            //info juego
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = review.gameTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white),
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                //las estrellitas mostradas están sujetas a la calificación
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = review.rating.toString(),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.brand_yellow), // #F3C430
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(review.rating) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Puntuación",
                                tint = colorResource(id = R.color.brand_primary), // #E83FB8
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                //TODO - Arreglar el formato de la fecha
                Text(
                    text = review.relativeDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = 12.sp
                )
            }


            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Ir a detalle",
                tint = colorResource(id = R.color.text_secondary),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF07090D)
@Composable
fun ProfileReviewsSectionPreview() {
    GamerCornerAppTheme {
        val mockReviews = listOf(
            ReviewItem(
                id = "1",
                gameTitle = "Elden Ring",
                rating = 5,
                relativeDate = "Hace 2 días",
                gameImageId = R.drawable.mini_elden,
                description = "Un juego increible..."
            ),
            ReviewItem(
                id = "2",
                gameTitle = "Cyberpunk",
                rating = 4,
                relativeDate = "Hace 1 semana",
                gameImageId = R.drawable.cyberpunk,
                description = "Una gran historia..."
            )
        )

        ProfileReviewsSection(reviews = mockReviews)
    }
}