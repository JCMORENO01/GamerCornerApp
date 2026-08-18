package com.example.gamercornerapp.ui.Screens.review.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun OpinionSection(
    opinion: String,
    onOpinionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(
                id = R.string.label_opinion
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


        OutlinedTextField(
            value = opinion,
            onValueChange = onOpinionChange,

            placeholder = {

                Text(
                    text = stringResource(
                        id = R.string.opinion_placeholder
                    ),
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 13.sp
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),

            shape = RoundedCornerShape(16.dp),

            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = colorResource(
                    id = R.color.brand_primary
                ),

                unfocusedBorderColor = colorResource(
                    id = R.color.card_background
                ),

                focusedContainerColor = colorResource(
                    id = R.color.card_background
                ),

                unfocusedContainerColor = colorResource(
                    id = R.color.card_background
                ),

                focusedTextColor = colorResource(
                    id = R.color.white
                ),

                unfocusedTextColor = colorResource(
                    id = R.color.white
                )
            )
        )


        Spacer(
            modifier = Modifier.height(6.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            Text(
                text = stringResource(
                    id = R.string.review_char_counter,
                    opinion.length
                ),
                color = colorResource(
                    id = R.color.text_secondary
                ),
                fontSize = 11.sp
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Opinion Section"
)
@Composable
fun OpinionSectionPreview() {

    GamerCornerAppTheme {

        Box(
            modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_background)
                )
                .padding(16.dp)
        ) {

            OpinionSection(
                opinion = "Este juego me pareció increíble.",
                onOpinionChange = { }
            )
        }
    }
}