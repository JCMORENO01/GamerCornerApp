package com.example.gamercornerapp.ui.Screens.register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun AlreadyAccountSection(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement =
            androidx.compose.foundation.layout.Arrangement.Center,

        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(
                id = R.string.has_account
            ),
            color =
                MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )


        Spacer(
            modifier = Modifier.width(4.dp)
        )


        Text(
            text = stringResource(
                id = R.string.login_link
            ),

            color =
                MaterialTheme.colorScheme.primary,

            fontSize = 12.sp,

            fontWeight =
                FontWeight.Bold,

            modifier = Modifier.clickable {
                onLoginClick()
            }
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
fun AlreadyAccountSectionPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        ) {

            AlreadyAccountSection(
                onLoginClick = { }
            )
        }
    }
}