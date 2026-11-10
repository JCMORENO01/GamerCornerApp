
package com.example.gamercornerapp.ui.Screens.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.gamercornerapp.ui.Screens.notes.NotesUiState
import com.example.gamercornerapp.ui.Screens.notes.NotesViewModel
import com.example.gamercornerapp.ui.Screens.notes.components.NotesSection
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun FeedScreen(
    onGameClick: (String) -> Unit,
    onReviewClick: (String) -> Unit = {},
    onUserClick: (String) -> Unit = {},
    onRadarClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel(),
    notesViewModel: NotesViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val notesUiState by notesViewModel.uiState.collectAsState()

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
        onReviewClick = onReviewClick,
        onUserClick = onUserClick,
        onRadarClick = onRadarClick,

        onLikeClick = viewModel::toggleReviewLike,
        onBookmarkClick = viewModel::toggleSaveReview,

        notesUiState = notesUiState,

        onCreateNote = notesViewModel::openEditor,
        onSelectNote = notesViewModel::selectNote,
        onDismissNote = notesViewModel::clearSelection,
        onCloseNoteEditor = notesViewModel::closeEditor,
        onSaveNote = notesViewModel::saveNote,
        onDeleteNote = notesViewModel::deleteNote,
        onDismissNoteError = notesViewModel::dismissError,

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
    onReviewClick: (String) -> Unit = {},
    onUserClick: (String) -> Unit = {},
    onRadarClick: () -> Unit = {},
    onLikeClick: (String) -> Unit = {},
    onBookmarkClick: (String) -> Unit = {},

    notesUiState: NotesUiState = NotesUiState(),
    onCreateNote: () -> Unit = {},
    onSelectNote: (String) -> Unit = {},
    onDismissNote: () -> Unit = {},
    onCloseNoteEditor: () -> Unit = {},
    onSaveNote: (String) -> Unit = {},
    onDeleteNote: () -> Unit = {},
    onDismissNoteError: () -> Unit = {},

    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // =====================================
        // ENCABEZADO ORIGINAL + RADAR
        // =====================================

        FeedTopBar(
            onRadarClick = onRadarClick
        )

        // =====================================
        // NOTAS GAMERCORNER
        // =====================================

        NotesSection(
            uiState = notesUiState,

            onCreateClick = onCreateNote,
            onNoteClick = onSelectNote,
            onDismissNote = onDismissNote,
            onCloseEditor = onCloseNoteEditor,
            onSaveNote = onSaveNote,
            onDeleteNote = onDeleteNote,
            onDismissError = onDismissNoteError,

            modifier = Modifier.padding(
                vertical = 12.dp
            )
        )

        // =====================================
        // PESTAÑAS DEL FEED
        // =====================================

        FeedTabs(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )

        // =====================================
        // ESTADO DE CARGA
        // =====================================

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

            // =================================
            // ERROR DEL FEED
            // =================================

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

            // =================================
            // FEED VACÍO
            // =================================

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

                // =============================
                // PUBLICACIONES EXISTENTES
                // =============================

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
                                onReviewClick(post.id)
                            },

                            onCommentClick = {
                                onReviewClick(post.id)
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
            onGameClick = {},
            onRadarClick = {},
            notesUiState = NotesUiState()
        )
    }
}
