package com.example.gamercornerapp.ui.Screens.videogame.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameSynopsis(
    description: String,
    modifier: Modifier = Modifier
) {

    Text(
        text = description,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        modifier = modifier
    )
}


@Preview(
    showBackground = true,
    name = "Videogame Synopsis Dark"
)
@Composable
fun VideogameSynopsisPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        VideogameSynopsis(
            description = "Una obra maestra. Un vasto mundo lleno de secretos, jefes épicos y una historia profunda que te atrapa desde el primer momento.",
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        )
    }
}