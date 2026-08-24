package com.example.gamercornerapp.ui.Screens.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppChip
import com.example.gamercornerapp.data.FeedPost
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.UserStats
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FeedPostCard(
    post: FeedPost,
    onLikeClick: () -> Unit = { },
    onCommentClick: () -> Unit = { },
    onShareClick: () -> Unit = { },
    onBookmarkClick: () -> Unit = { },
    onMoreClick: () -> Unit = { },
    onCardClick: () -> Unit = { },
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onCardClick()
            },

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            // Encabezado: avatar, usuario, tiempo y menu
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(
                        id = post.author.profileImageId
                    ),

                    contentDescription =
                        "Foto de ${post.author.username}",

                    contentScale = ContentScale.Crop,

                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                )


                Spacer(
                    modifier = Modifier.width(10.dp)
                )


                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = post.author.username,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )


                    Text(
                        text = post.relativeTime,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }


                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más opciones",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,

                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onMoreClick()
                        }
                )
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // Contenido: portada del juego + informacion
            Row {

                Image(
                    painter = painterResource(
                        id = post.game.image
                    ),

                    contentDescription =
                        "Portada de ${post.game.title}",

                    contentScale = ContentScale.Crop,

                    modifier = Modifier
                        .size(
                            width = 78.dp,
                            height = 100.dp
                        )
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(
                            MaterialTheme.colorScheme.background
                        )
                )


                Spacer(
                    modifier = Modifier.width(12.dp)
                )


                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = post.game.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )


                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = post.rating.toString(),
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )


                        Spacer(
                            modifier = Modifier.width(4.dp)
                        )


                        Row {

                            repeat(5) {

                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }


                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )


                    Text(
                        text = post.description,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 17.sp
                    )


                    if (post.tags.isNotEmpty()) {

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )


                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            post.tags.forEach { tag ->

                                AppChip(
                                    text = tag,
                                    modifier = Modifier.height(30.dp),

                                    backgroundColor =
                                        MaterialTheme.colorScheme.secondary
                                            .copy(alpha = 0.35f)
                                )
                            }
                        }
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(14.dp)
            )


            // Acciones: like, comentarios, compartir y guardar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = if (post.isLiked) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },

                    contentDescription = "Me gusta",

                    tint = MaterialTheme.colorScheme.primary,

                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onLikeClick()
                        }
                )


                Spacer(
                    modifier = Modifier.width(6.dp)
                )


                Text(
                    text = post.likesCount.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )


                Spacer(
                    modifier = Modifier.width(20.dp)
                )


                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "Comentarios",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,

                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            onCommentClick()
                        }
                )


                Spacer(
                    modifier = Modifier.width(6.dp)
                )


                Text(
                    text = post.commentsCount.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )


                Spacer(
                    modifier = Modifier.width(20.dp)
                )


                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Compartir",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,

                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            onShareClick()
                        }
                )


                Spacer(
                    modifier = Modifier.weight(1f)
                )


                Icon(
                    imageVector = if (post.isBookmarked) {
                        Icons.Default.Bookmark
                    } else {
                        Icons.Default.BookmarkBorder
                    },

                    contentDescription = "Guardar",

                    tint = MaterialTheme.colorScheme.onSurfaceVariant,

                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onBookmarkClick()
                        }
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Feed Post Card"
)
@Composable
fun FeedPostCardPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        FeedPostCard(
            post = FeedPost(

                id = "1",

                author = UserProfile(
                    username = "NightHunter",
                    nickName = "@nighthunter_21",
                    bio = "Vivo para los videojuegos 🎮",
                    profileBackgroundId =
                        R.drawable.background_maquinitas,
                    profileBgDescription =
                        "Imagen de monitores",
                    profileImageId =
                        R.drawable.messi1,

                    stats = UserStats(
                        reviewsCount = 128,
                        followersCount = 342,
                        followingCount = 176
                    )
                ),

                relativeTime = "Hace 2 horas",

                game = Game(
                    title = "Elden Ring",
                    developer = "FromSoftware",
                    year = 2022,
                    image = R.drawable.mini_elden
                ),

                rating = 4.8,

                description =
                    "Simplemente una obra maestra. El mundo, la historia, los jefes... Todo aquí te reta y te recompensa. Inolvidable.",

                tags = listOf(
                    "RPG",
                    "Mundo Abierto"
                ),

                likesCount = 256,
                commentsCount = 42
            ),

            modifier = Modifier.padding(16.dp)
        )
    }
}