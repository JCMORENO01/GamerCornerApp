package com.example.gamercornerapp.ui.Screens.feed.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import com.example.gamercornerapp.ui.model.FeedPost

@Composable
fun FeedPostCard(
    post: FeedPost,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onCardClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_background)
        ),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            // Encabezado: avatar, usuario, tiempo y menú de opciones
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = post.userAvatarImage),
                    contentDescription = "Foto de ${post.username}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.username,
                        color = colorResource(id = R.color.white),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post.relativeTime,
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 12.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más opciones",
                    tint = colorResource(id = R.color.text_secondary),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onMoreClick() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Contenido: portada del juego + info de la reseña
            Row {
                Image(
                    painter = painterResource(id = post.gameImageId),
                    contentDescription = "Portada de ${post.gameTitle}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 78.dp, height = 100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(id = R.color.brand_background))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.gameTitle,
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.rating.toString(),
                            color = colorResource(id = R.color.brand_yellow),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Row {
                            repeat(5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.brand_primary),
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = post.description,
                        color = colorResource(id = R.color.text_secondary),
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 17.sp
                    )

                    if (post.tags.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            post.tags.forEach { tag ->
                                FeedTagChip(text = tag)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Acciones: like, comentarios, compartir y guardar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Me gusta",
                    tint = colorResource(id = R.color.brand_primary),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onLikeClick() }
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = post.likesCount.toString(),
                    color = colorResource(id = R.color.brand_primary),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "Comentarios",
                    tint = colorResource(id = R.color.text_secondary),
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { onCommentClick() }
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = post.commentsCount.toString(),
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Compartir",
                    tint = colorResource(id = R.color.text_secondary),
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { onShareClick() }
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = if (post.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Guardar",
                    tint = colorResource(id = R.color.text_secondary),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onBookmarkClick() }
                )
            }
        }
    }
}

// Chip morado usado para las etiquetas del juego dentro del muro (RPG, Acción, etc.)
@Composable
private fun FeedTagChip(text: String) {
    Box(
        modifier = Modifier
            .background(
                colorResource(id = R.color.brand_purple).copy(alpha = 0.35f),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = colorResource(id = R.color.white),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF07090D)
@Composable
fun FeedPostCardPreview() {
    GamerCornerAppTheme {
        FeedPostCard(
            post = FeedPost(
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
            modifier = Modifier.padding(16.dp)
        )
    }
}
