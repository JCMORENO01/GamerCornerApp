package com.example.gamercornerapp.ui.Screens.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
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
fun CreateAccountSection(
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(
                id = R.string.no_account
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
        )


        Text(
            text = stringResource(
                id = R.string.create_account
            ),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable {
                    onCreateAccountClick()
                }
                .padding(start = 4.dp)
        )
    }
}


@Preview(
    showBackground = true,
    name = "Create Account Dark"
)
@Composable
fun CreateAccountSectionPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(24.dp)
        ) {

            CreateAccountSection(
                onCreateAccountClick = { }
            )
        }
    }
}