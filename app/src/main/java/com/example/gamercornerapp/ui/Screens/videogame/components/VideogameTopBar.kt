package com.example.gamercornerapp.ui.Screens.videogame.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameTopBar(
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(
            onClick = onBackClick,

            modifier = Modifier
                .size(40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    CircleShape
                ),

            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(
                    id = R.string.content_description_back
                )
            )
        }


        IconButton(
            onClick = onShareClick,

            modifier = Modifier
                .size(40.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    CircleShape
                ),

            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {

            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(
                    id = R.string.content_description_share
                )
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Videogame Top Bar Dark"
)
@Composable
fun VideogameTopBarPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        VideogameTopBar(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        )
    }
}