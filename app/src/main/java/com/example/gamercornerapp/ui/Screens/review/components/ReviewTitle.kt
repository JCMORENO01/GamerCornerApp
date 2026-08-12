package com.example.gamercornerapp.ui.Screens.review.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R

@Composable
fun ReviewTitle() {

    Text(
        text = stringResource(
            id = R.string.title_write_review
        ),
        color = colorResource(
            id = R.color.white
        ),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(
            bottom = 20.dp
        )
    )
}