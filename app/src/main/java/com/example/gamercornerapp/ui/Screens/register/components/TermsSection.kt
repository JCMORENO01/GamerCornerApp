package com.example.gamercornerapp.ui.Screens.register.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
fun TermsSection(
    checked: Boolean,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = { },
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(
                    id = R.color.brand_primary
                ),
                uncheckedColor = colorResource(
                    id = R.color.text_secondary
                ),
                checkmarkColor = colorResource(
                    id = R.color.white
                )
            ),
            modifier = Modifier.size(20.dp)
        )


        Spacer(
            modifier = Modifier.width(6.dp)
        )


        Text(
            text = stringResource(
                id = R.string.terms_complete_text
            ),
            color = colorResource(
                id = R.color.text_secondary
            ),
            fontSize = 10.sp
        )
    }
}


// Escenario 1
@Preview(
    showBackground = true,
)
@Composable
fun TermsUncheckedPreview() {

    GamerCornerAppTheme {

        Box(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(16.dp)
        ) {

            TermsSection(
                checked = false
            )
        }
    }
}


// Escenario 2
@Preview(
    showBackground = true,
)
@Composable
fun TermsCheckedPreview() {

    GamerCornerAppTheme {

        Box(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(16.dp)
        ) {

            TermsSection(
                checked = true
            )
        }
    }
}