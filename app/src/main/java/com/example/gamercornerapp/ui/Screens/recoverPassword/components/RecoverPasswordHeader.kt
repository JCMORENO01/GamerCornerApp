package com.example.gamercornerapp.ui.Screens.recoverPassword.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.ui.componentes.LoginLogo

@Composable
fun RecoverPasswordHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LoginLogo()

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Recuperar contraseña",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Te enviaremos un enlace para restablecer tu contraseña",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.85f)
        )
    }
}