package com.example.gamercornerapp.ui.Screens.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FeedTopBar(
    onMenuClick: () -> Unit = { },
    onSettingsClick: () -> Unit = { },
    modifier: Modifier = Modifier
) {

    androidx.compose.foundation.layout.Row(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 16.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menú",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(26.dp)
                .clickable {
                    onMenuClick()
                }
        )


        val isDark = isSystemInDarkTheme()
        val logoRes = if (isDark) {
            R.drawable.logo_gamer1      // Logo para tema negro
        } else {
            R.drawable.logo_fondo_claro // Logo para tema blanco
        }

        Image(
            painter = painterResource(id = logoRes),
            contentDescription = stringResource(
                id = R.string.logo_content_description
            ),
            modifier = Modifier.size(120.dp)
        )


        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Ajustes",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onSettingsClick()
                }
        )
    }
}


@Preview(
    showBackground = true,
    name = "Feed Top Bar"
)
@Composable
fun FeedTopBarPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.background
                )
        ) {

            FeedTopBar()
        }
    }
}