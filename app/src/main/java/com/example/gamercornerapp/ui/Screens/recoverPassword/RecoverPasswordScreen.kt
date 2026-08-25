package com.example.gamercornerapp.ui.Screens.recoverPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.recoverPassword.components.RecoverPasswordFooter
import com.example.gamercornerapp.ui.Screens.recoverPassword.components.RecoverPasswordHeader
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun RecoverPasswordScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onSendLinkClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }

    RecoverPasswordScreenContent(
        email = email,
        onEmailChange = { email = it },
        onBackClick = onBackClick,
        onLoginClick = onLoginClick,
        onSendLinkClick = { onSendLinkClick(email) },
        modifier = modifier
    )
}


@Composable
fun RecoverPasswordScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onSendLinkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow),
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .align(Alignment.TopStart)
                .clickable { onBackClick() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))


            RecoverPasswordHeader()

            Spacer(modifier = Modifier.height(36.dp))


            AppTextField(
                value = email,
                onValueChange = onEmailChange,
                label = stringResource(
                    id = R.string.email_or_user_hint
                ),
                placeholder = stringResource(
                    id = R.string.hint_email
                )
            )

            Spacer(modifier = Modifier.height(28.dp))


            AppButton(
                text = "Enviar enlace",
                onClick = onSendLinkClick
            )

            Spacer(modifier = Modifier.weight(1f))


            RecoverPasswordFooter(
                onLoginClick = onLoginClick
            )
            Spacer(modifier = Modifier.height(54.dp))
        }
    }
}

@Preview(
    showBackground = true,
    name = "Recover Password Dark"
)
@Composable
fun RecoverPasswordScreenPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        RecoverPasswordScreen(
            onBackClick = {},
            onLoginClick = {},
            onSendLinkClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Recover Password Light"
)
@Composable
fun RecoverPasswordScreenLightPreview() {
    GamerCornerAppTheme(darkTheme = false) {
        RecoverPasswordScreen(
            onBackClick = {},
            onLoginClick = {},
            onSendLinkClick = {}
        )
    }
}