package com.example.gamercornerapp.ui.Screens.recoverPassword.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun RecoverPasswordForm(
    email: String,
    onEmailChange: (String) -> Unit,
    onSendLinkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(id = R.string.email_or_user_hint),
            placeholder = stringResource(id = R.string.hint_email)
        )

        Spacer(modifier = Modifier.height(28.dp))

        AppButton(
            text = stringResource(id = R.string.btn_send_link),
            onClick = onSendLinkClick
        )
    }
}

@Preview(
    showBackground = true,
    name = "Recover Password Form Dark"
)
@Composable
fun RecoverPasswordFormDarkPreview() {
    GamerCornerAppTheme(darkTheme = true) {
        RecoverPasswordForm(
            email = "juan@gmail.com",
            onEmailChange = {},
            onSendLinkClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Recover Password Form Light"
)
@Composable
fun RecoverPasswordFormLightPreview() {
    GamerCornerAppTheme(darkTheme = false) {
        RecoverPasswordForm(
            email = "juan@gmail.com",
            onEmailChange = {},
            onSendLinkClick = {}
        )
    }
}