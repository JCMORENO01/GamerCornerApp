package com.example.gamercornerapp.ui.Screens.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.gamercornerapp.ui.model.GameCategory
import com.example.gamercornerapp.ui.model.local.LocalDataProvider
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun CategoryChipsSection(
    categories: List<GameCategory>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSeeAllClick: () -> Unit = { }
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(
                    id = R.string.title_categories
                ),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )


            Text(
                text = stringResource(
                    id = R.string.btn_see_all
                ),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    onSeeAllClick()
                }
            )
        }


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(
                items = categories,
                key = { it.name }
            ) { category ->

                CategoryChip(
                    category = category,
                    isSelected = category.name == selectedCategory,
                    onClick = {
                        onCategorySelected(category.name)
                    }
                )
            }
        }
    }
}


@Composable
private fun CategoryChip(
    category: GameCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }


    Row(
        modifier = modifier
            .height(38.dp)
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(backgroundColor)
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = category.icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(16.dp)
        )


        Spacer(
            modifier = Modifier.width(6.dp)
        )


        Text(
            text = category.name,
            color = contentColor,
            fontSize = 13.sp,
            fontWeight = if (isSelected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            }
        )
    }
}


@Preview(
    showBackground = true,
    name = "Category Chips Section Dark"
)
@Composable
fun CategoryChipsSectionPreview() {

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

            CategoryChipsSection(
                categories = LocalDataProvider.exploreCategories,
                selectedCategory = "Todos",
                onCategorySelected = { }
            )
        }
    }
}
