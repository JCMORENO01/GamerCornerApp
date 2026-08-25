package com.example.gamercornerapp.ui.Screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R

@Composable
fun LoginLogo () {
    val isDark = isSystemInDarkTheme()
    val logoRes = if (isDark) {
        R.drawable.logo_gamer1      // Blanco para modo oscuro
    } else {
        R.drawable.logo_fondo_claro // Negro para modo claro
    }

    Image(
        painter = painterResource(
            id = logoRes
        ),
        contentDescription = stringResource(
            id = R.string.logo_content_description
        ),
        modifier = Modifier
            .width(500.dp)
            .height(200.dp)
    )
}