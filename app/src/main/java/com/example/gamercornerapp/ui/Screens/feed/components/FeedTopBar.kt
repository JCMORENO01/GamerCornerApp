package com.example.gamercornerapp.ui.Screens.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun FeedTopBar(
    onMenuClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
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
            tint = colorResource(id = R.color.white),
            modifier = Modifier
                .size(26.dp)
                .clickable { onMenuClick() }
        )

        Image(
            painter = painterResource(id = R.drawable.logo_gamer1),
            contentDescription = stringResource(id = R.string.logo_content_description),
            modifier = Modifier.size(120.dp)
        )

        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Ajustes",
            tint = colorResource(id = R.color.white),
            modifier = Modifier
                .size(24.dp)
                .clickable { onSettingsClick() }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF07090D)
@Composable
fun FeedTopBarPreview() {
    GamerCornerAppTheme {
        FeedTopBar()
    }
}