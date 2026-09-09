package com.example.gamercornerapp.ui.Screens.selfProfile.components

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ProfileReviewsSection(
    reviews: List<ReviewItem>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onSeeAllClick: () -> Unit = {},
    onReviewClick: (ReviewItem) -> Unit = {}
) {

    val tabs = listOf(
        stringResource(id = R.string.tab_reviews),
        stringResource(id = R.string.tab_saved)
    )


    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            divider = {},

            indicator = {

                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        selectedTabIndex
                    ),
                    height = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {

            tabs.forEachIndexed { index, title ->

                val isSelected =
                    selectedTabIndex == index


                val textColor by animateColorAsState(
                    targetValue = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    label = "tabTextColor"
                )


                Tab(
                    selected = isSelected,

                    onClick = {
                        onTabSelected(index)
                    },

                    modifier = Modifier.background(
                        if (isSelected) {
                            MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.15f
                            )
                        } else {
                            Color.Transparent
                        }
                    ),

                    text = {

                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Medium
                            },
                            color = textColor
                        )
                    }
                )
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (selectedTabIndex == 0) {
                    stringResource(id = R.string.title_recent_reviews)
                } else {
                    stringResource(id = R.string.title_recent_saved)
                },
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                letterSpacing = 0.8.sp,
                fontSize = 12.sp
            )


            Text(
                text = stringResource(id = R.string.btn_see_all),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                modifier = Modifier.clickable {
                    onSeeAllClick()
                }
            )
        }


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // Por cada review aparece una tarjeta
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            reviews.forEach { review ->

                ReviewCardItem(
                    review = review,
                    onClick = {
                        onReviewClick(review)
                    }
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
            .clickable {
                onClick()
            },

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                painter = painterResource(
                    id = review.gameImageId
                ),
                contentDescription = stringResource(
                    id = R.string.cd_game_cover,
                    review.gameTitle
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(
                        width = 60.dp,
                        height = 64.dp
                    )
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.background
                    )
            )


            Spacer(
                modifier = Modifier.width(14.dp)
            )


            // Informacion del juego
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = review.gameTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp
                )


                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                // Calificacion
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = review.rating.toString(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 14.sp
                    )


                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        repeat(review.rating) {

                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(id = R.string.cd_score),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }


                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                Text(
                    text = review.relativeDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }


            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.cd_go_to_detail),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Profile Reviews Dark"
)
@Composable
fun ProfileReviewsSectionPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(vertical = 16.dp)
        ) {

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


            ProfileReviewsSection(
                reviews = mockReviews,
                selectedTabIndex = 0,
                onTabSelected = { }
            )
        }
    }
}