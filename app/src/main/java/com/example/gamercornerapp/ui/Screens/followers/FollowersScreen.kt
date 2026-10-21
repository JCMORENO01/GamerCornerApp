
package com.example.gamercornerapp.ui.Screens.followers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamercornerapp.data.FollowerItem
import com.example.gamercornerapp.ui.Screens.followers.components.FollowersHeader
import com.example.gamercornerapp.ui.Screens.followers.components.FollowersTabs
import com.example.gamercornerapp.ui.Screens.followers.components.UserRow

@Composable
fun FollowersScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    userId: String = "",
    tabIndex: Int = 0,
    onUserClick: (String) -> Unit = {},
    viewModel: FollowersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId, tabIndex) {
        viewModel.loadFollowers(userId, tabIndex)
    }

    FollowersScreenContent(
        followers = uiState.followers,
        following = uiState.following,
        selectedTabIndex = uiState.selectedTabIndex,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onTabSelected = viewModel::onTabSelected,
        onFollowClick = viewModel::toggleFollowUser,
        onUserClick = onUserClick,
        onBackClick = onBackClick,
        onSearchClick = onSearchClick,
        modifier = modifier
    )
}

@Composable
fun FollowersScreenContent(
    followers: List<FollowerItem>,
    following: List<FollowerItem>,
    selectedTabIndex: Int,
    isLoading: Boolean,
    errorMessage: String?,
    onTabSelected: (Int) -> Unit,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit = {},
    onFollowClick: (String) -> Unit = {},
    onUserClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val visibleUsers = if (selectedTabIndex == 0) {
        followers
    } else {
        following
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        FollowersHeader(onBackClick = onBackClick)

        Button(
            onClick = onSearchClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Buscar usuarios...")
        }

        FollowersTabs(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (visibleUsers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (selectedTabIndex == 0) {
                        "Este usuario todavía no tiene seguidores"
                    } else {
                        "Este usuario todavía no sigue a nadie"
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = visibleUsers,
                    key = { it.id }
                ) { user ->
                    UserRow(
                        user = user,
                        onUserClick = {
                            onUserClick(user.id)
                        },
                        onFollowClick = {
                            onFollowClick(user.id)
                        }
                    )
                }
            }
        }
    }
}
