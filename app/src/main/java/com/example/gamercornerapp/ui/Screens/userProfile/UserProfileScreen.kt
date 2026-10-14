
package com.example.gamercornerapp.ui.Screens.userProfile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun UserProfileScreen(
    userId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onReviewClick: (ReviewItem) -> Unit = {},
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUserProfile(userId)
    }

    UserProfileScreenContent(
        userProfile = uiState.userProfile,
        reviews = uiState.reviews,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onBackClick = onBackClick,
        onReviewClick = onReviewClick,
        onFollowClick = viewModel::toggleFollowUser,
        modifier = modifier
    )
}

@Composable
fun UserProfileScreenContent(
    userProfile: UserProfile?,
    reviews: List<ReviewItem>,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onReviewClick: (ReviewItem) -> Unit = {},
    onFollowClick: () -> Unit = {},
    errorMessage: String? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
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

                userProfile?.let { profile ->
                    ProfileHeaderSection(
                        userProfile = profile,
                        isLoadingImage = false,
                        isOwnProfile = false,
                        onLogoutClick = {},
                        onImageSelected = {}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Función para seguir o dejar de seguir
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (profile.isFollowed) {
                            OutlinedButton(
                                onClick = onFollowClick
                            ) {
                                Text("Siguiendo")
                            }
                        } else {
                            Button(
                                onClick = onFollowClick
                            ) {
                                Text("Seguir")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ProfileStatsSection(
                        stats = profile.stats,
                        onFollowersClick = {}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ProfileReviewsSection(
                        reviews = reviews,
                        selectedTabIndex = 0,
                        onTabSelected = {},
                        onReviewClick = onReviewClick
                    )
                } ?: run {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage ?: "Usuario no encontrado",
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
fun UserProfileScreenPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        UserProfileScreenContent(
            userProfile = LocalDataProvider.katanaGamerProfile,
            reviews = LocalDataProvider.reviews,
            isLoading = false,
            onBackClick = {}
        )
    }
}
