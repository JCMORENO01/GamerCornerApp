package com.example.gamercornerapp.ui.Screens.userProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun UserProfileScreenContent(
    userProfile: UserProfile?,
    reviews: List<ReviewItem>,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column {
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
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                userProfile?.let { profile ->
                    ProfileHeaderSection(
                        userProfile = profile,
                        isLoadingImage = false,
                        isOwnProfile = false, // ¡Aquí está la clave!
                        onLogoutClick = {},
                        onImageSelected = {}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ProfileStatsSection(
                        stats = profile.stats,
                        onFollowersClick = {}
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ProfileReviewsSection(
                        reviews = reviews,
                        selectedTabIndex = 0,
                        onTabSelected = {}
                    )
                } ?: run {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Usuario no encontrado",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "UserProfile Preview")
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
