package com.example.gamercornerapp.ui.Screens.reviewDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.ui.Screens.feed.components.FeedPostCard
import com.example.gamercornerapp.ui.componentes.AppButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    // En un futuro, podrías recibir el reviewId y pedirle al ViewModel que lo cargue,
    // pero por ahora podemos pasarle el post directamente o un ID para buscarlo.
    post: FeedPost?,
    onBackClick: () -> Unit,
    onNavigateToGame: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Detalle de Reseña", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground
            )
        )

        if (post == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Reutilizamos la misma tarjeta del feed que ya está lista y bonita
            FeedPostCard(
                post = post,
                onLikeClick = { /* Lógica de like */ },
                onCommentClick = { /* No hace nada en esta pantalla, o hace focus a un input */ },
                onShareClick = { /* Lógica de share */ },
                onAuthorClick = { /* Puedes agregar la navegación aquí después */ },
                onCardClick = { onNavigateToGame(post.game.id) }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Botón que lleva a ver el juego en la pantalla de "VideogameScreen"
            AppButton(
                text = "Ver detalles de ${post.game.title}",
                onClick = { onNavigateToGame(post.game.id) }
            )
        }
    }
}
