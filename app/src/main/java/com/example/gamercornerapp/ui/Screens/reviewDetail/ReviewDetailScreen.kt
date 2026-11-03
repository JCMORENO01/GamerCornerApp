package com.example.gamercornerapp.ui.Screens.reviewDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamercornerapp.ui.Screens.feed.components.FeedPostCard
import com.example.gamercornerapp.ui.componentes.AppButton

@Composable
fun ReviewDetailScreen(
    onBackClick: () -> Unit,
    onNavigateToGame: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val post = uiState.post

    LaunchedEffect(Unit) {
        viewModel.loadPost()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Encabezado superior directo sin duplicación de Insets ni Scaffold anidado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Detalle de Reseña",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (uiState.isLoading || post == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                FeedPostCard(
                    post = post,
                    onLikeClick = { viewModel.toggleReviewLike() },
                    onBookmarkClick = { viewModel.toggleSaveReview() },
                    onCommentClick = { },
                    onShareClick = { },
                    onAuthorClick = { },
                    onCardClick = { onNavigateToGame(post.game.id) }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                AppButton(
                    text = "Ver detalles de ${post.game.title}",
                    onClick = { onNavigateToGame(post.game.id) }
                )
            }
        }
    }
}
