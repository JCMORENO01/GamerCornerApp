
package com.example.gamercornerapp.ui.Screens.reviewMap.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.gamercornerapp.data.MapReview
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import com.mapbox.geojson.Point
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

@Composable
fun GamerReviewMarker(
    review: MapReview,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    ViewAnnotation(
        options = viewAnnotationOptions {
            geometry(
                Point.fromLngLat(
                    review.longitude,
                    review.latitude
                )
            )

            annotationAnchor {
                anchor(ViewAnnotationAnchor.BOTTOM)
            }

            allowOverlap(true)
        }
    ) {
        GamerMarkerContent(
            rating = review.rating,
            isSelected = isSelected,
            onClick = onClick
        )
    }
}

@Composable
private fun GamerMarkerContent(
    rating: Float,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val markerColor = when {
        isSelected -> Color(0xFF00E676)
        rating >= 4.5f -> Color(0xFFF3C430)
        else -> Color(0xFFE83FB8)
    }

    val animatedColor by animateColorAsState(
        targetValue = markerColor,
        label = "MarkerColor"
    )

    val animatedSize by animateDpAsState(
        targetValue = if (isSelected) 54.dp else 42.dp,
        label = "MarkerSize"
    )

    Box(
        modifier = Modifier
            .size(animatedSize)
            .shadow(
                elevation = if (isSelected) 12.dp else 5.dp,
                shape = CircleShape
            )
            .background(
                color = animatedColor,
                shape = CircleShape
            )
            .border(
                width = if (isSelected) 3.dp else 2.dp,
                color = Color.White,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.SportsEsports,
            contentDescription = "Ver reseña",
            tint = Color.White,
            modifier = Modifier.size(
                if (isSelected) 28.dp else 23.dp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GamerMarkerContentPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        GamerMarkerContent(
            rating = 4.8f,
            isSelected = false,
            onClick = {}
        )
    }
}
