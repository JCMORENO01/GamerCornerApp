package com.example.gamercornerapp.ui.Screens.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ExploreTopBar(
    onFiltersClick: () -> Unit = { },
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Text(
                text = stringResource(
                    id = R.string.explore_eyebrow
                ).uppercase(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )


            Text(
                text = stringResource(
                    id = R.string.explore_title
                ),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }


        IconButton(
            onClick = onFiltersClick,

            modifier = Modifier
                .size(44.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    CircleShape
                ),

            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {

            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = stringResource(
                    id = R.string.content_description_filters
                )
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Explore Top Bar Dark"
)
@Composable
fun ExploreTopBarPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
        ) {

            ExploreTopBar()
        }
    }
}
