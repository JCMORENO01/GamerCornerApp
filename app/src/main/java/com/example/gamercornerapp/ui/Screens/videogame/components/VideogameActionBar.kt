package com.example.gamercornerapp.ui.Screens.videogame.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.componentes.AppButton
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun VideogameActionBar(
    onSaveClick: () -> Unit = {},
    onWriteReviewClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedButton(
            onClick = onSaveClick,

            modifier = Modifier
                .height(56.dp)
                .weight(0.4f),

            shape = RoundedCornerShape(16.dp),

            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),

            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
        ) {

            Icon(
                imageVector = Icons.Filled.BookmarkBorder,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )


            Text(
                text = stringResource(
                    id = R.string.btn_save
                ),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 8.dp
                )
            )
        }


        AppButton(
            text = stringResource(
                id = R.string.btn_write_review
            ),
            onClick = onWriteReviewClick,
            modifier = Modifier
                .height(56.dp)
                .weight(0.6f)
        )
    }
}


@Preview(
    showBackground = true,
    name = "Videogame Action Bar Dark"
)
@Composable
fun VideogameActionBarPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        VideogameActionBar(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp)
        )
    }
}