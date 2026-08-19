package com.example.gamercornerapp.ui.Screens.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun FeedTabs(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val tabs = listOf(
        "Para ti",
        "Siguiendo"
    )

    val lineColor = MaterialTheme.colorScheme.surfaceVariant


    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {

                val strokeWidth = 2.dp.toPx()

                drawLine(
                    color = lineColor,
                    start = Offset(
                        0f,
                        size.height - strokeWidth / 2
                    ),
                    end = Offset(
                        size.width,
                        size.height - strokeWidth / 2
                    ),
                    strokeWidth = strokeWidth
                )
            }
    ) {

        tabs.forEachIndexed { index, title ->

            val isSelected = selectedTabIndex == index


            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onTabSelected(index)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = title,

                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onBackground
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },

                    fontSize = 15.sp,

                    fontWeight = if (isSelected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Medium
                    },

                    modifier = Modifier.padding(
                        vertical = 12.dp
                    )
                )


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(
                            if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.Transparent
                            }
                        )
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Feed Tabs"
)
@Composable
fun FeedTabsPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.background
                )
        ) {

            FeedTabs(
                selectedTabIndex = 0,
                onTabSelected = { }
            )
        }
    }
}