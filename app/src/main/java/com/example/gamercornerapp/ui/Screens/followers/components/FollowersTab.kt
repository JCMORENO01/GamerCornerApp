package com.example.gamercornerapp.ui.Screens.followers.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FollowersTabs(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(24.dp)
            )
            .padding(4.dp)
    ) {

        // Seguidores
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(
                    MaterialTheme.colorScheme.background
                ),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = stringResource(
                    id = R.string.tab_followers
                ),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }


        // Siguiendo
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = stringResource(
                    id = R.string.tab_following
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FollowersTabsPreview() {

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

            FollowersTabs()
        }
    }
}