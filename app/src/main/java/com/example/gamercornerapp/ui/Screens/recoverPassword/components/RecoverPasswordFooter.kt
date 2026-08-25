package com.example.gamercornerapp.ui.Screens.recoverPassword.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun RecoverPasswordFooter(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Ya recordaste tu contraseña? ",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
        )

        Text(
            text = "Inicia sesión",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onLoginClick() }
        )
    }
}