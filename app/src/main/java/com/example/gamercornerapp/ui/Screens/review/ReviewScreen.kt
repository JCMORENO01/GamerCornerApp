
package com.example.gamercornerapp.ui.Screens.review

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.ui.Screens.review.components.GameInfoCard
import com.example.gamercornerapp.ui.Screens.review.components.OpinionSection
import com.example.gamercornerapp.ui.Screens.review.components.RatingSection
import com.example.gamercornerapp.ui.Screens.review.components.ReviewLocationSection
import com.example.gamercornerapp.ui.Screens.review.components.ReviewTitle
import com.example.gamercornerapp.ui.Screens.review.components.TagsSection
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun ReviewScreen(
    gameId: String,
    reviewId: String? = null,
    initialOpinion: String? = null,
    initialRating: Int? = null,
    onPublishClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    var includeLocation by rememberSaveable {
        mutableStateOf(false)
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted = permissions.values.any { it }

            // El usuario puede continuar sin ubicación.
            viewModel.publishReview(
                gameId = gameId,
                includeLocation = granted
            )
        }

    LaunchedEffect(gameId, reviewId) {
        viewModel.loadGame(
            gameId = gameId,
            reviewId = reviewId,
            initialOpinion = initialOpinion,
            initialRating = initialRating
        )
    }

    LaunchedEffect(uiState.isPublishSuccess) {
        if (uiState.isPublishSuccess) {
            onPublishClick()
        }
    }

    val game = uiState.game

    if (uiState.isLoading && game == null) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }

    } else if (game != null) {

        ReviewScreenContent(
            game = game,
            rating = uiState.rating,
            opinion = uiState.opinion,
            selectedTags = uiState.selectedTags,
            isLoading = uiState.isLoading,
            errorMessage = uiState.errorMessage,
            isEditing = reviewId != null,
            includeLocation = includeLocation,
            onLocationChange = {
                includeLocation = it
            },
            onErrorDismiss = viewModel::onErrorDismiss,
            onRatingChange = viewModel::onRatingChange,
            onOpinionChange = viewModel::onOpinionChange,
            onTagToggle = viewModel::onTagToggle,
            onPublishClick = {

                if (reviewId != null || !includeLocation) {

                    viewModel.publishReview(
                        gameId = gameId,
                        includeLocation = false
                    )

                } else {

                    val finePermission =
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                    val coarsePermission =
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                    if (finePermission || coarsePermission) {

                        viewModel.publishReview(
                            gameId = gameId,
                            includeLocation = true
                        )

                    } else {

                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                    }
                }
            },
            modifier = modifier
        )

    } else {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = uiState.errorMessage
                    ?: stringResource(R.string.error_game_not_found),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun ReviewScreenContent(
    game: Game,
    rating: Int,
    opinion: String,
    onRatingChange: (Int) -> Unit,
    onOpinionChange: (String) -> Unit,
    onPublishClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedTags: Set<String> = emptySet(),
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onErrorDismiss: () -> Unit = {},
    onTagToggle: (String) -> Unit = {},
    isEditing: Boolean = false,
    includeLocation: Boolean = false,
    onLocationChange: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage != null) {

            Card(
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.errorContainer
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { onErrorDismiss() }
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme
                            .onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = MaterialTheme.colorScheme
                            .onErrorContainer
                    )
                }
            }
        }

        ReviewTitle()

        GameInfoCard(game = game)

        Spacer(modifier = Modifier.height(24.dp))

        RatingSection(
            rating = rating,
            onRatingChange = onRatingChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        OpinionSection(
            opinion = opinion,
            onOpinionChange = onOpinionChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        TagsSection(
            selectedTags = selectedTags,
            onTagToggle = onTagToggle
        )

        if (!isEditing) {

            Spacer(modifier = Modifier.height(24.dp))

            ReviewLocationSection(
                includeLocation = includeLocation,
                onLocationChange = onLocationChange
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        AppButton(
            text = if (isLoading) {
                "Guardando..."
            } else {
                stringResource(R.string.btn_publish_review)
            },
            onClick = {
                if (!isLoading) {
                    onPublishClick()
                }
            }
        )
    }
}

@Preview(showBackground = true, name = "Elden Ring")
@Composable
fun ReviewScreenEldenRingPreview() {

    GamerCornerAppTheme(darkTheme = true) {

        ReviewScreenContent(
            game = Game(
                id = "1",
                title = "Elden Ring",
                developer = "FromSoftware",
                year = 2022,
                image = R.drawable.elden
            ),
            rating = 5,
            opinion = "",
            selectedTags = setOf("Historia"),
            includeLocation = true,
            onLocationChange = {},
            onRatingChange = {},
            onOpinionChange = {},
            onTagToggle = {},
            onPublishClick = {}
        )
    }
}
