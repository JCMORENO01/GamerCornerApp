package com.example.gamercornerapp.ui.Screens.feed



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.feed.components.FeedPostCard
import com.example.gamercornerapp.ui.Screens.feed.components.FeedTabs
import com.example.gamercornerapp.ui.Screens.feed.components.FeedTopBar
import com.example.gamercornerapp.ui.componentes.GamerBottomBar
import com.example.gamercornerapp.ui.model.FeedPost
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun FeedScreen(
    posts: List<FeedPost>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = colorResource(id = R.color.brand_background),
        bottomBar = {
            GamerBottomBar(selectedTab = 0)
        }
    ) { innerPadding ->
        FeedScreenContent(
            posts = posts,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FeedScreenContent(
    posts: List<FeedPost>,
    modifier: Modifier = Modifier,
    onPostClick: (FeedPost) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        FeedTopBar()

        FeedTabs(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it }
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(posts, key = { it.id }) { post ->
                FeedPostCard(
                    post = post,
                    onCardClick = { onPostClick(post) }
                )
            }
        }
    }
}


@Preview
@Composable
fun FeedScreenPreview() {
    GamerCornerAppTheme {
        val samplePosts = listOf(
            FeedPost(
                id = "1",
                username = "NightHunter",
                userAvatarImage = R.drawable.messi1,
                relativeTime = "Hace 2 horas",
                gameTitle = "Elden Ring",
                rating = 4.8,
                description = "Simplemente una obra maestra. El mundo, la historia, los jefes... Todo aquí te reta y te recompensa. Inolvidable.",
                gameImageId = R.drawable.mini_elden,
                tags = listOf("RPG", "Mundo Abierto"),
                likesCount = 256,
                commentsCount = 42
            ),
            FeedPost(
                id = "2",
                username = "KatanaGamer",
                userAvatarImage = R.drawable.messi2,
                relativeTime = "Hace 4 horas",
                gameTitle = "God of War",
                rating = 4.7,
                description = "Una secuela épica. Combate brutal, historia increíble y personajes que te marcan.",
                gameImageId = R.drawable.cyberpunk,
                tags = listOf("Acción", "Aventura"),
                likesCount = 198,
                commentsCount = 31
            )
        )

        FeedScreen(posts = samplePosts)
    }
}
