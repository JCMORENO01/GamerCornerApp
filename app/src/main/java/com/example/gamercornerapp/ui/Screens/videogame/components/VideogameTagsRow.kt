package com.example.gamercornerapp.ui.Screens.videogame.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.ui.componentes.AppChip
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameTagsRow(
    tags: List<String>,
    modifier: Modifier = Modifier
) {

    val backgroundColor = MaterialTheme.colorScheme.secondary.copy(
        alpha = 0.1f
    )
    val contentColor = MaterialTheme.colorScheme.secondary

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(tags) { tag ->

            AppChip(
                text = tag,
                backgroundColor = backgroundColor,
                textColor = contentColor,
                modifier = Modifier
                    .height(32.dp),
                border = BorderStroke(
                    1.dp,
                    contentColor.copy(alpha = 0.5f)
                )
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Videogame Tags Dark"
)
@Composable
fun VideogameTagsRowPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Row(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        ) {

            VideogameTagsRow(
                tags = listOf(
                    "RPG",
                    "Mundo Abierto",
                    "Fantasía"
                )
            )
        }
    }
}