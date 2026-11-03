package com.example.gamercornerapp.ui.Screens.reviewDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.Comment
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.UserStats
import com.example.gamercornerapp.ui.Screens.feed.components.FeedPostCard
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

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
        // Encabezado superior
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
                    .padding(horizontal = 16.dp)
                    .imePadding()
                    .navigationBarsPadding()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        FeedPostCard(
                            post = post,
                            onLikeClick = { viewModel.toggleReviewLike() },
                            onBookmarkClick = { viewModel.toggleSaveReview() },
                            onCommentClick = { },
                            onShareClick = { },
                            onAuthorClick = { },
                            onCardClick = { onNavigateToGame(post.game.id) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        AppButton(
                            text = "Ver detalles de ${post.game.title}",
                            onClick = { onNavigateToGame(post.game.id) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Comentarios (${uiState.comments.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(uiState.comments) { comment ->
                        CommentItemCard(comment = comment)
                    }
                }

                // Barra inferior para comentar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = uiState.commentInput,
                        onValueChange = { viewModel.onCommentInputChanged(it) },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Escribe un comentario...") },
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { viewModel.postReviewComment() },
                        enabled = !uiState.isLoadingComment && uiState.commentInput.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Enviar comentario",
                            tint = if (uiState.commentInput.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommentItemCard(comment: Comment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = comment.userName.ifBlank { "Usuario" },
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, name = "ReviewDetailScreen Con Comentarios Preview")
@Composable
fun ReviewDetailScreenWithCommentsPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        val sampleGame = Game(
            id = "1",
            title = "Elden Ring",
            developer = "FromSoftware",
            year = 2022,
            image = R.drawable.elden
        )

        val sampleAuthor = UserProfile(
            id = "2",
            username = "KatanaGamer",
            nickName = "@katana_gamer",
            bio = "Fan de los souls-like 🗡️",
            profileBackgroundId = R.drawable.background_maquinitas,
            profileBgDescription = "Imagen de monitores",
            profileImageId = R.drawable.messi2,
            stats = UserStats(
                reviewsCount = 64,
                followersCount = 210,
                followingCount = 98
            )
        )

        val samplePost = FeedPost(
            id = "review_1",
            author = sampleAuthor,
            relativeTime = "Hace 2 horas",
            game = sampleGame,
            rating = 5.0,
            description = "¡Una obra maestra absoluta! El diseño de mundo abierto y los jefes son increíbles.",
            tags = listOf("RPG", "Acción"),
            likesCount = 10,
            commentsCount = 2,
            isLiked = false,
            isBookmarked = false
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Detalle de Reseña",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                FeedPostCard(
                    post = samplePost,
                    onLikeClick = {},
                    onCommentClick = {},
                    onShareClick = {},
                    onAuthorClick = {},
                    onCardClick = {}
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Comentarios (2)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                CommentItemCard(
                    comment = Comment(
                        commentId = "c1",
                        userId = "u1",
                        userName = "Carlos Dev",
                        text = "¡Totalmente de acuerdo! Malenia me costó Dios y ayuda, pero el juego es increíble.",
                        timestamp = com.google.firebase.Timestamp.now()
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                CommentItemCard(
                    comment = Comment(
                        commentId = "c2",
                        userId = "u2",
                        userName = "MariaGamer",
                        text = "El apartado artístico es de lo mejor que he visto en años.",
                        timestamp = com.google.firebase.Timestamp.now()
                    )
                )
            }
        }
    }
}
