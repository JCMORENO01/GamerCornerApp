package com.example.gamercornerapp.ui.Screens.review.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppChip

@Composable
fun TagsSection() {

    Column {

        Text(
            text = stringResource(
                id = R.string.label_tags
            ),
            color = colorResource(
                id = R.color.white
            ),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppChip(
                text = stringResource(
                    id = R.string.tag_history
                )
            )

            AppChip(
                text = stringResource(
                    id = R.string.tag_gameplay
                )
            )
        }
    }
}