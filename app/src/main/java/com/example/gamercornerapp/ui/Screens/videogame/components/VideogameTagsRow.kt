package com.example.gamercornerapp.ui.Screens.videogame.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppChip
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameTagsRow(
    tags: List<String>,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(tags) { tag ->

            AppChip(
                text = tag,
                backgroundColor = colorResource(id = R.color.brand_purple).copy(alpha = 0.35f),
                textColor = colorResource(id = R.color.white)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VideogameTagsRowPreview() {

    GamerCornerAppTheme {

        Row(
            modifier = Modifier
                .background(colorResource(id = R.color.brand_background))
                .padding(16.dp)
        ) {

            VideogameTagsRow(
                tags = listOf("RPG", "Mundo Abierto", "Fantasía")
            )
        }
    }
}
