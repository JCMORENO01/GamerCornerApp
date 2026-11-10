
package com.example.gamercornerapp.ui.Screens.reviewMap.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.example.gamercornerapp.data.MapReview
import com.example.gamercornerapp.ui.theme.LocalIsDarkTheme

import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.ColorValue
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.ThemeValue
import com.mapbox.maps.extension.compose.style.standard.rememberStandardStyleState

@Composable
fun GamerMap(
    reviews: List<MapReview>,
    selectedReviewId: String?,
    onMarkerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    // =====================================
    // TEMA DE GAMERCORNER
    // =====================================

    val isDarkTheme = LocalIsDarkTheme.current

    // =====================================
    // CONFIGURACIÓN MAPBOX STANDARD
    // =====================================

    val standardStyleState = rememberStandardStyleState()

    SideEffect {

        standardStyleState.configurationsState.apply {

            theme = ThemeValue.DEFAULT

            lightPreset = if (isDarkTheme) {
                LightPresetValue.NIGHT
            } else {
                LightPresetValue.DAY
            }

            // =================================
            // EDIFICIOS Y ELEMENTOS 3D
            // =================================

            show3dObjects = BooleanValue(true)
            show3dBuildings = BooleanValue(true)

            // =================================
            // AUTOPISTAS
            // =================================

            colorMotorways = ColorValue(
                if (isDarkTheme) {
                    Color(0xFFFF4DB8)
                } else {
                    Color(0xFFDE3E9F)
                }
            )

            // =================================
            // VÍAS PRINCIPALES
            // =================================

            colorTrunks = ColorValue(
                if (isDarkTheme) {
                    Color(0xFFB835A0)
                } else {
                    Color(0xFFEA85BE)
                }
            )

            // =================================
            // CALLES SECUNDARIAS
            // =================================

            colorRoads = ColorValue(
                if (isDarkTheme) {
                    Color(0xFF692A68)
                } else {
                    Color(0xFFEBC7DF)
                }
            )

            // =================================
            // DETALLES DE LAS VÍAS
            // =================================

            colorHdRoads = ColorValue(
                if (isDarkTheme) {
                    Color(0xFFB835A0)
                } else {
                    Color(0xFFEA85BE)
                }
            )

            // =================================
            // EDIFICIOS 3D
            // =================================

            colorBuildings = ColorValue(
                if (isDarkTheme) {
                    Color(0xFF42304F)
                } else {
                    Color(0xFFD5C5E4)
                }
            )

            // =================================
            // TERRENO
            // =================================

            colorLand = ColorValue(
                if (isDarkTheme) {
                    Color(0xFF101018)
                } else {
                    Color(0xFFF8F6FA)
                }
            )

            // =================================
            // AGUA
            // =================================

            colorWater = ColorValue(
                if (isDarkTheme) {
                    Color(0xFF172B40)
                } else {
                    Color(0xFFDCEAF7)
                }
            )

            // =================================
            // ZONAS VERDES
            // =================================

            colorGreenspace = ColorValue(
                if (isDarkTheme) {
                    Color(0xFF183526)
                } else {
                    Color(0xFFCDECCF)
                }
            )

            // =================================
            // PUNTOS DE INTERÉS
            // =================================

            colorPointOfInterestLabels = ColorValue(
                if (isDarkTheme) {
                    Color(0xFFC49BDA)
                } else {
                    Color(0xFF7F3EDF)
                }
            )

            // =================================
            // LÍMITES ADMINISTRATIVOS
            // =================================

            colorAdminBoundaries = ColorValue(
                if (isDarkTheme) {
                    Color(0xFF49405A)
                } else {
                    Color(0xFFD4C9DD)
                }
            )

            // =================================
            // ZONAS COMERCIALES
            // =================================

            colorCommercial = ColorValue(
                if (isDarkTheme) {
                    Color(0xFF35203A)
                } else {
                    Color(0xFFF3DEEF)
                }
            )

            // =================================
            // ZONAS EDUCATIVAS
            // =================================

            colorEducation = ColorValue(
                if (isDarkTheme) {
                    Color(0xFFD9A145)
                } else {
                    Color(0xFFFFD68A)
                }
            )

            // =================================
            // ZONAS MÉDICAS
            // =================================

            colorMedical = ColorValue(
                if (isDarkTheme) {
                    Color(0xFFB74D65)
                } else {
                    Color(0xFFF8A5A5)
                }
            )
        }
    }

    // =====================================
    // CÁMARA 3D
    // =====================================

    val viewportState = rememberMapViewportState {
        setCameraOptions {
            center(
                Point.fromLngLat(
                    -74.0817,
                    4.6097
                )
            )

            zoom(16.2)
            pitch(65.0)
            bearing(-25.0)
        }
    }

    // =====================================
    // CENTRAR EN LAS RESEÑAS
    // =====================================

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
                zoom(16.2)
                pitch(65.0)
                bearing(-25.0)
            }
        }
    }

    // =====================================
    // MAPBOX
    // =====================================

    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = viewportState,

        style = {
            MapboxStandardStyle(
                standardStyleState = standardStyleState
            )
        }
    ) {

        // =================================
        // MARCADORES DE RESEÑAS
        // =================================

        reviews.forEach { review ->

            GamerReviewMarker(
                review = review,
                isSelected =
                    review.id == selectedReviewId,
                onClick = {
                    onMarkerClick(review.id)
                }
            )
        }
    }
}
