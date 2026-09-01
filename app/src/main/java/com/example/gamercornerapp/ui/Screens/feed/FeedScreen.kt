package com.example.gamercornerapp.ui.Screens.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.ui.Screens.feed.components.FeedPostCard
import com.example.gamercornerapp.ui.Screens.feed.components.FeedTabs
import com.example.gamercornerapp.ui.Screens.feed.components.FeedTopBar
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FeedScreen(
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FeedScreenContent(
        posts = uiState.posts,
        selectedTabIndex = uiState.selectedTabIndex,
        onTabSelected = viewModel::onTabSelected,
        onGameClick = onGameClick,
        modifier = modifier
    )
}


@Composable
fun FeedScreenContent(
    posts: List<FeedPost>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        FeedTopBar()


        FeedTabs(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )


        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            ),

            verticalArrangement = Arrangement.spacedBy(
                16.dp
            ),

            modifier = Modifier.fillMaxSize()
        ) {

            items(
                items = posts,
                key = {
                    it.id
                }
            ) { post ->

                FeedPostCard(
                    post = post,

                    onCardClick = {

                        onGameClick(
                            post.game.id
                        )
                    }
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Feed Dark"
)
@Composable
fun FeedScreenPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        FeedScreen(
            onGameClick = { }
        )
    }
}