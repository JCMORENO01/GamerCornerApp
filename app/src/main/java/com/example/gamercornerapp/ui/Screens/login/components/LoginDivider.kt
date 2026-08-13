package com.example.gamercornerapp.ui.Screens.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun LoginDivider(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = colorResource(
                id = R.color.card_background
            )
        )

        Text(
            text = stringResource(
                id = R.string.login_or_separator
            ),
            color = colorResource(
                id = R.color.text_secondary
            ),
            fontSize = 12.sp,
            modifier = Modifier.padding(
                horizontal = 8.dp
            )
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = colorResource(
                id = R.color.card_background
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginDividerPreview() {

    GamerCornerAppTheme {

        Column(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(24.dp)
        ) {

            LoginDivider()
        }
    }
}