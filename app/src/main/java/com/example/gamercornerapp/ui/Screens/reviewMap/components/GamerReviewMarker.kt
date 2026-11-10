
package com.example.gamercornerapp.ui.Screens.reviewMap.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.gamercornerapp.data.MapReview
import com.example.gamercornerapp.ui.theme.BrandPrimary
import com.example.gamercornerapp.ui.theme.BrandPurple
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation

@Composable
fun GamerReviewMarker(
    review: MapReview,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    CircleAnnotation(
        point = Point.fromLngLat(
            review.longitude,
            review.latitude
        )
    ) {

        circleRadius = if (isSelected) {
            14.0
        } else {
            10.0
        }

        circleColor = if (isSelected) {
            BrandPrimary
        } else {
            BrandPurple
        }

        circleStrokeColor = Color.White

        circleStrokeWidth = if (isSelected) {
            3.0
        } else {
            2.0
        }

        interactionsState.onClicked {
            onClick()
            true
        }
    }
}
