package com.example.gamercornerapp.ui.Screens.review.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.gamercornerapp.ui.componentes.AppChip
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun TagsSection(
    modifier: Modifier = Modifier,
    selectedTags: Set<String> = emptySet(),
    onTagToggle: (String) -> Unit = {},
    onAddTagClick: () -> Unit = {}
) {
    val defaultTags = listOf(
        stringResource(id = R.string.tag_history),
        stringResource(id = R.string.tag_gameplay),
        stringResource(id = R.string.tag_graphics)
    )

    Column(modifier = modifier) {

        Text(
            text = stringResource(
                id = R.string.label_tags
            ),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            defaultTags.forEach { tag ->
                val isSelected = selectedTags.contains(tag)

                AppChip(
                    text = tag,
                    backgroundColor = if (isSelected) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    textColor = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        }
                    ),
                    onClick = { onTagToggle(tag) }
                )
            }

            // Botón de agregar nueva etiqueta (+)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        ),
                        CircleShape
                    )
                    .clickable { onAddTagClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.plus_sign),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Tags Section"
)
@Composable
fun TagsSectionPreview() {

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

            TagsSection(
                selectedTags = setOf("Historia")
            )
        }
    }
}