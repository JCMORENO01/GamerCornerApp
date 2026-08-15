package com.example.gamercornerapp.ui.Screens.selfProfile.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.UserStats
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun ProfileStatsSection(
    stats: UserStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(
            // Fondo oscuro para la tarjeta extraído de colors.xml
            containerColor = colorResource(id = R.color.card_background) // #323039
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(
                count = stats.reviewsCount,
                label = "RESEÑAS"
            )
            StatItem(
                count = stats.followersCount,
                label = "SEGUIDORES"
            )
            StatItem(
                count = stats.followingCount,
                label = "SIGUIENDO"
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
            color = colorResource(id = R.color.white),
            fontSize = 20.sp
        )


        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.text_secondary), // #94a3b8
            letterSpacing = 0.5.sp,
            fontSize = 11.sp
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ProfileStatsSectionPreview() {
    GamerCornerAppTheme {
        val sampleStats = UserStats(
            reviewsCount = 128,
            followersCount = 342,
            followingCount = 176
        )

        ProfileStatsSection(stats = sampleStats)
    }
}