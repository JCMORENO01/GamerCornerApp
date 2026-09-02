package com.example.gamercornerapp.ui.Screens.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ExploreSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = { }
) {

    val gradientButton = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
        )
    )


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(
                RoundedCornerShape(28.dp)
            )
            .background(
                MaterialTheme.colorScheme.surface
            )
            .padding(
                start = 16.dp,
                end = 6.dp
            ),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(
                id = R.string.content_description_search
            ),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )


        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        ) {

            if (query.isEmpty()) {

                Text(
                    text = stringResource(
                        id = R.string.search_placeholder
                    ),
                    color = MaterialTheme.colorScheme
                        .onSurfaceVariant
                        .copy(alpha = 0.6f),
                    fontSize = 14.sp
                )
            }


            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                ),
                cursorBrush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary
                    )
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }


        IconButton(
            onClick = onSearchClick,

            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(gradientButton)
        ) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(
                    id = R.string.content_description_search
                ),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Explore Search Bar Dark"
)
@Composable
fun ExploreSearchBarPreview() {

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

            ExploreSearchBar(
                query = "",
                onQueryChange = { }
            )
        }
    }
}
