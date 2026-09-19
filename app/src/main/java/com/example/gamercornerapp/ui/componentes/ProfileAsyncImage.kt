package com.example.gamercornerapp.ui.componentes

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gamercornerapp.R

@Composable
fun ProfileAsyncImage(
    profileImage: Any?,
    size: Dp = 100.dp,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = profileImage,
        contentDescription = "Foto de perfil",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.loading_img),
        error = painterResource(id = R.drawable.ic_broken_image),
        modifier = modifier
            .size(size)
            .clip(CircleShape)
    )
}