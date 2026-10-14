package com.example.gamercornerapp.ui.Screens.selfProfile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.local.LocalDataProvider
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileHeaderSection
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileReviewsSection
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileStatsSection
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun SelfProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: SelfProfileViewModel = hiltViewModel(),
    onFollowersClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onEditReviewClick: (ReviewItem) -> Unit = {},
    onReviewClick: (ReviewItem) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    SelfProfileScreenContent(
        userProfile = uiState.userProfile,
        reviews = uiState.reviews,
        selectedTabIndex = uiState.selectedTabIndex,
        isLoading = uiState.isLoading,
        isLoadingImage = uiState.isLoadingImage,
        errorMessage = uiState.errorMessage,
        onTabSelected = viewModel::onTabSelected,
        onFollowersClick = onFollowersClick,
        onLogoutClick = {
            viewModel.onLogoutClick()
            onLogoutClick()
        },
        onSettingsClick = onSettingsClick,
        onImageSelected = viewModel::uploadImageToFirebase,
        onEditReviewClick = onEditReviewClick,
        onDeleteReviewClick = { review -> viewModel.deleteReview(review.id) },
        onReviewClick = onReviewClick,
        modifier = modifier
    )
}

@Composable
fun SelfProfileScreenContent(
    userProfile: UserProfile?,
    reviews: List<ReviewItem>,
    selectedTabIndex: Int,
    isLoading: Boolean,
    isLoadingImage: Boolean,
    errorMessage: String?,
    onTabSelected: (Int) -> Unit,
    onFollowersClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    onEditReviewClick: (ReviewItem) -> Unit,
    onDeleteReviewClick: (ReviewItem) -> Unit,
    onReviewClick: (ReviewItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    )
                }

                if (userProfile != null) {
                    ProfileHeaderSection(
                        userProfile = userProfile,
                        isLoadingImage = isLoadingImage,
                        isOwnProfile = true,
                        onSettingsClick = onSettingsClick,
                        onLogoutClick = onLogoutClick,
                        onImageSelected = onImageSelected
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ProfileStatsSection(
                        stats = userProfile.stats,
                        onFollowersClick = onFollowersClick
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ProfileReviewsSection(
                        reviews = reviews,
                        selectedTabIndex = selectedTabIndex,
                        onTabSelected = onTabSelected,
                        onReviewClick = onReviewClick,
                        onEditClick = onEditReviewClick,
                        onDeleteClick = onDeleteReviewClick
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage ?: "Error al cargar perfil",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelfProfileScreenPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        SelfProfileScreenContent(
            userProfile = LocalDataProvider.katanaGamerProfile,
            reviews = LocalDataProvider.reviews,
            selectedTabIndex = 0,
            isLoading = false,
            isLoadingImage = false,
            errorMessage = null,
            onTabSelected = {},
            onFollowersClick = {},
            onLogoutClick = {},
            onSettingsClick = {},
            onImageSelected = {},
            onEditReviewClick = {},
            onDeleteReviewClick = {},
            onReviewClick = {}
        )
    }
}
