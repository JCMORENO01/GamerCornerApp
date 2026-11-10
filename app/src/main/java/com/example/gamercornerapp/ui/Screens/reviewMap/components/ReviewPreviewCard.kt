
package com.example.gamercornerapp.ui.Screens.reviewMap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.data.MapReview
import com.example.gamercornerapp.ui.theme.BrandPrimary
import com.example.gamercornerapp.ui.theme.BrandYellow
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import java.util.concurrent.TimeUnit

@Composable
fun ReviewPreviewCard(
    review: MapReview,
    onDismiss: () -> Unit,
    onViewDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "REVIEW DETECTADA",
                    color = BrandPrimary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                TextButton(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }

            Text(
                text = review.gameTitle,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "@${review.username}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "★ ${review.rating}",
                    color = BrandYellow,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = review.description,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = formatReviewTime(review.createdAtMillis),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall
            )

            Button(
                onClick = onViewDetail,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandPrimary
                )
            ) {
                Text(
                    text = "Ver reseña completa",
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
        }
    }
}

private fun formatReviewTime(
    createdAtMillis: Long
): String {

    val elapsed = (
            System.currentTimeMillis() - createdAtMillis
            ).coerceAtLeast(0L)

    val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed)

    return when {
        minutes < 1L -> "Hace un momento"
        minutes < 60L -> "Hace $minutes min"
        else -> {
            val hours = TimeUnit.MILLISECONDS.toHours(elapsed)
            "Hace $hours h"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewPreviewCardPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        ReviewPreviewCard(
            review = MapReview(
                id = "review-1",
                gameTitle = "Cyberpunk 2077",
                username = "Gamer01",
                description = "Excelente ambientación y una historia increíble.",
                rating = 4.5f,
                latitude = 4.6097,
                longitude = -74.0817,
                createdAtMillis = System.currentTimeMillis()
            ),
            onDismiss = {},
            onViewDetail = {}
        )
    }
}
