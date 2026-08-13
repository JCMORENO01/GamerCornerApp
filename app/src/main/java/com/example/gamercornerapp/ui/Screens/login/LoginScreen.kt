package com.example.gamercornerapp.ui.Screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.Screens.login.components.CreateAccountSection
import com.example.gamercornerapp.ui.Screens.login.components.GoogleLoginButton
import com.example.gamercornerapp.ui.Screens.login.components.LoginDivider
import com.example.gamercornerapp.ui.Screens.login.components.LoginForm
import com.example.gamercornerapp.ui.Screens.login.components.LoginHeader
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {

    LoginScreenContent(
        modifier = modifier
    )
}


@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.brand_background)
            )
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginHeader()

            Spacer(
                modifier = Modifier.height(28.dp)
            )


            LoginForm()

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            LoginDivider()

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            GoogleLoginButton()

            Spacer(
                modifier = Modifier.height(32.dp)
            )


            CreateAccountSection()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    GamerCornerAppTheme {
        LoginScreen()
    }
}