package com.example.gamercornerapp.ui.Screens.selfProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.gamercornerapp.data.UserStats
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ProfileStatsSection(
    stats: UserStats,
    onFollowersClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 18.dp,
                    horizontal = 8.dp
                ),

            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {


            // Reseñas
            StatItem(
                count = stats.reviewsCount,
                label = stringResource(id = R.string.label_reviews_stat)
            )


            // Seguidores
            Box(
                modifier = Modifier.clickable {
                    onFollowersClick()
                }
            ) {

                StatItem(
                    count = stats.followersCount,
                    label = stringResource(id = R.string.label_followers_stat)
                )
            }


            // Siguiendo
            StatItem(
                count = stats.followingCount,
                label = stringResource(id = R.string.label_following_stat)
            )
        }
    }
}


@Composable
private fun StatItem(
    count: Int,
    label: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 20.sp
        )


        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp,
            fontSize = 11.sp
        )
    }
}


@Preview(
    showBackground = true,
    name = "Profile Stats Dark"
)
@Composable
fun ProfileStatsSectionPreview() {

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

            val sampleStats = UserStats(
                reviewsCount = 128,
                followersCount = 342,
                followingCount = 176
            )


            ProfileStatsSection(
                stats = sampleStats,
                onFollowersClick = { }
            )
        }
    }
}