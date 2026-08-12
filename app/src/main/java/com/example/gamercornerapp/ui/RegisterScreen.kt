package com.example.gamercornerapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.componentes.AppButton
import com.example.gamercornerapp.componentes.AppTextField
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.brand_background))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.title_create_account),
                color = colorResource(id = R.color.white),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.subtitle_create_account),
                color = colorResource(id = R.color.text_secondary),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            AppTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = stringResource(id = R.string.label_fullname),
                placeholder = stringResource(id = R.string.hint_fullname)
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                value = username,
                onValueChange = { username = it },
                label = stringResource(id = R.string.label_username),
                placeholder = stringResource(id = R.string.hint_username)
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(id = R.string.label_email),
                placeholder = stringResource(id = R.string.hint_email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(id = R.string.label_password),
                placeholder = stringResource(id = R.string.hint_password),
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = stringResource(id = R.string.label_confirm_password),
                placeholder = stringResource(id = R.string.hint_confirm_password),
                isPassword = true
            )

            Spacer(modifier = Modifier.height(40.dp))

            AppButton(
                text = stringResource(id = R.string.btn_action_create_account),
                onClick = { }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.has_account),
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = 13.sp
                )
                Text(
                    text = stringResource(id = R.string.login_link),
                    color = colorResource(id = R.color.brand_primary),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    GamerCornerAppTheme {
        RegisterScreen()
    }
}