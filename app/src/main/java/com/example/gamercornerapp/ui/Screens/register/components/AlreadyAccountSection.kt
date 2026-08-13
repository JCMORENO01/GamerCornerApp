package com.example.gamercornerapp.ui.Screens.register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun AlreadyAccountSection(
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(
                id = R.string.has_account
            ),
            color = colorResource(
                id = R.color.text_secondary
            ),
            fontSize = 12.sp
        )


        Spacer(
            modifier = Modifier.width(4.dp)
        )


        Text(
            text = stringResource(
                id = R.string.login_link
            ),
            color = colorResource(
                id = R.color.brand_primary
            ),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AlreadyAccountSectionPreview() {

    GamerCornerAppTheme {

        Box(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(16.dp)
        ) {

            AlreadyAccountSection()
        }
    }
}