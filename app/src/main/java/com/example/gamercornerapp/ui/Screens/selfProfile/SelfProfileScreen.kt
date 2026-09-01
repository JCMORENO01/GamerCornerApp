package com.example.gamercornerapp.ui.Screens.selfProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamercornerapp.data.ReviewItem
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileHeaderSection
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileReviewsSection
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileStatsSection
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun SelfProfileScreen(
    onFollowersClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SelfProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    uiState.userProfile?.let { profile ->
        SelfProfileScreenContent(
            userProfile = profile,
            reviews = uiState.reviews,
            selectedTabIndex = uiState.selectedTabIndex,
            onTabSelected = viewModel::onTabSelected,
            onFollowersClick = onFollowersClick,
            modifier = modifier
        )
    }
}


@Composable
fun SelfProfileScreenContent(
    userProfile: UserProfile,
    reviews: List<ReviewItem>,

    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,

    onFollowersClick: () -> Unit,

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

            ProfileHeaderSection(
                userProfile = userProfile
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