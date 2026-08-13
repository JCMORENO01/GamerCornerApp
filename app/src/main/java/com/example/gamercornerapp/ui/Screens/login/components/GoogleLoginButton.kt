package com.example.gamercornerapp.ui.Screens.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier
) {

    OutlinedButton(
        onClick = { },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = colorResource(
                id = R.color.white
            ),
            containerColor = colorResource(
                id = R.color.card_background
            )
        ),
        border = null
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.logo_google
                ),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = stringResource(
                    id = R.string.google_login
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GoogleLoginButtonPreview() {

    GamerCornerAppTheme {

        Column(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(24.dp)
        ) {

            GoogleLoginButton()
        }
    }
}