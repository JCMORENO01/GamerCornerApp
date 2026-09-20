package com.example.gamercornerapp.ui.Screens.selfProfile

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileHeaderSection
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileReviewsSection
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileStatsSection
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun SelfProfileScreen(
    onFollowersClick: () -> Unit,
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: SelfProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    uiState.userProfile?.let { profile ->
        SelfProfileScreenContent(
            userProfile = profile,
            reviews = uiState.reviews,
            selectedTabIndex = uiState.selectedTabIndex,
            isLoadingImage = uiState.isLoadingImage,
            errorMessage = uiState.errorMessage,
            onErrorDismiss = viewModel::onErrorDismiss,
            onTabSelected = viewModel::onTabSelected,
            onFollowersClick = onFollowersClick,
            onLogoutClick = {
                viewModel.onLogoutClick()
                onLogoutClick()
            },
            onImageSelected = { uri ->
                viewModel.uploadImageToFirebase(uri)
            },
            modifier = modifier
        )
    }
}


@Composable
fun SelfProfileScreenContent(
    userProfile: UserProfile,
    reviews: List<ReviewItem>,
    selectedTabIndex: Int,
    isLoadingImage: Boolean = false,
    errorMessage: String? = null,
    onErrorDismiss: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    onFollowersClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onImageSelected: (android.net.Uri) -> Unit = {},
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 0.dp
            )
    ) {

        Column {

            if (errorMessage != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }

            ProfileHeaderSection(
                userProfile = userProfile,
                isLoadingImage = isLoadingImage,
                onLogoutClick = onLogoutClick,
                onImageSelected = onImageSelected
            )


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            ProfileStatsSection(
                stats = userProfile.stats,
                onFollowersClick = onFollowersClick
            )


            Spacer(
                modifier = Modifier.height(30.dp)
            )


            ProfileReviewsSection(
                reviews = reviews,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = onTabSelected
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Self Profile Dark"
)
@Composable
fun SelfProfileScreenPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        SelfProfileScreen(
            onFollowersClick = { }
        )
    }
}
