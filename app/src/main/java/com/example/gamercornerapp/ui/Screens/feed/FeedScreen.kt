
package com.example.gamercornerapp.ui.Screens.feed

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
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.ui.Screens.feed.components.FeedPostCard
import com.example.gamercornerapp.ui.Screens.feed.components.FeedTabs
import com.example.gamercornerapp.ui.Screens.feed.components.FeedTopBar
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun FeedScreen(
    onGameClick: (String) -> Unit,
    onUserClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFeed()
    }

    FeedScreenContent(
        posts = uiState.posts,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        selectedTabIndex = uiState.selectedTabIndex,
        onTabSelected = viewModel::onTabSelected,
        onGameClick = onGameClick,
        onUserClick = onUserClick,
        onLikeClick = viewModel::toggleReviewLike,
        onBookmarkClick = viewModel::toggleSaveReview,
        modifier = modifier
    )
}

@Composable
fun FeedScreenContent(
    posts: List<FeedPost>,
    isLoading: Boolean,
    errorMessage: String?,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onGameClick: (String) -> Unit,
    onUserClick: (String) -> Unit = {},
    onLikeClick: (String) -> Unit = {},
    onBookmarkClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        FeedTopBar()

        FeedTabs(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )

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

            if (posts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (selectedTabIndex == 1) {
                            "Todavía no hay publicaciones de usuarios que sigues"
                        } else {
                            "No hay publicaciones"
                        },
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = posts,
                        key = { it.id }
                    ) { post ->
                        FeedPostCard(
                            post = post,
                            onAuthorClick = {
                                onUserClick(post.author.id)
                            },
                            onCardClick = {
                                onGameClick(post.game.id)
                            },
                            onLikeClick = {
                                onLikeClick(post.id)
                            },
                            onBookmarkClick = {
                                onBookmarkClick(post.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        FeedScreenContent(
            posts = emptyList(),
            isLoading = false,
            errorMessage = null,
            selectedTabIndex = 0,
            onTabSelected = {},
            onGameClick = {}
        )
    }
}
