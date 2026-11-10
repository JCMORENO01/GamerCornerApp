
package com.example.gamercornerapp.ui.Screens.reviewMap.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.gamercornerapp.data.MapReview
import com.example.gamercornerapp.ui.theme.LocalIsDarkTheme
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle

@Composable
fun GamerMap(
    reviews: List<MapReview>,
    selectedReviewId: String?,
    onMarkerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    // Obtenemos el tema actual de GamerCorner
    val isDarkTheme = LocalIsDarkTheme.current

    // Seleccionamos el estilo de Mapbox
    val mapStyle = if (isDarkTheme) {
        Style.DARK
    } else {
        Style.LIGHT
    }

    val viewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(0.0, 0.0))
            zoom(1.5)
        }
    }

    val firstReviewId = reviews.firstOrNull()?.id

    LaunchedEffect(firstReviewId) {

        val firstReview = reviews.firstOrNull()

        if (firstReview != null) {

            viewportState.setCameraOptions {
                center(
                    Point.fromLngLat(
                        firstReview.longitude,
                        firstReview.latitude
                    )
                )
                zoom(10.0)
            }
        }
    }

    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = viewportState,
        style = {
            MapStyle(style = mapStyle)
        }
    ) {

        reviews.forEach { review ->

            GamerReviewMarker(
                review = review,
                isSelected = review.id == selectedReviewId,
                onClick = {
                    onMarkerClick(review.id)
                }
            )
        }
    }
}
