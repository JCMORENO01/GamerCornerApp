
package com.example.gamercornerapp.ui.Screens.reviewMap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamercornerapp.ui.Screens.reviewMap.components.GamerMap
import com.example.gamercornerapp.ui.Screens.reviewMap.components.MapHeader
import com.example.gamercornerapp.ui.Screens.reviewMap.components.ReviewPreviewCard

@Composable
fun ReviewMapScreen(
    onReviewClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewMapViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ReviewMapScreenContent(
        uiState = uiState,
        onMarkerClick = viewModel::selectReview,
        onDismissReview = viewModel::clearSelection,
        onRetry = viewModel::loadReviews,
        onReviewClick = onReviewClick,
        modifier = modifier
    )
}

@Composable
fun ReviewMapScreenContent(
    uiState: ReviewMapUiState,
    onMarkerClick: (String) -> Unit,
    onDismissReview: () -> Unit,
    onRetry: () -> Unit,
    onReviewClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        GamerMap(
            reviews = uiState.reviews,
            selectedReviewId = uiState.selectedReviewId,
            onMarkerClick = onMarkerClick,
            modifier = Modifier.fillMaxSize()
        )

        MapHeader(
            reviewCount = uiState.reviews.size,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(16.dp)
        )

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (!uiState.isLoading &&
            uiState.errorMessage != null
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Button(onClick = onRetry) {
                    Text("Reintentar")
                }
            }
        }

        if (!uiState.isLoading &&
            uiState.errorMessage == null &&
            uiState.reviews.isEmpty()
        ) {
            Text(
                text = "No hay reseñas con ubicación en las últimas 24 horas",
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(20.dp)
            )
        }

        uiState.selectedReview?.let { review ->

            ReviewPreviewCard(
                review = review,
                onDismiss = onDismissReview,
                onViewDetail = {
                    onReviewClick(review.id)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
