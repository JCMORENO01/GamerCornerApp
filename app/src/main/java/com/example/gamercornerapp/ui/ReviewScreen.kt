package com.example.gamercornerapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.componentes.AppButton
import com.example.gamercornerapp.componentes.AppChip
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ReviewScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.brand_background)
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Titulo
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


        // Informacion del juego
        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(16.dp),

            colors = CardDefaults.cardColors(
                containerColor = colorResource(
                    id = R.color.card_background
                )
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(
                        id = R.string.game_title
                    ),
                    color = colorResource(
                        id = R.color.white
                    ),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = stringResource(
                        id = R.string.game_subtitle
                    ),
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 13.sp
                )
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // Calificacion
        Text(
            text = stringResource(
                id = R.string.label_rating
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


        // Estrellas
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = "★",
                color = colorResource(
                    id = R.color.brand_primary
                ),
                fontSize = 32.sp
            )

            Text(
                text = "★",
                color = colorResource(
                    id = R.color.brand_primary
                ),
                fontSize = 32.sp
            )

            Text(
                text = "★",
                color = colorResource(
                    id = R.color.brand_primary
                ),
                fontSize = 32.sp
            )

            Text(
                text = "★",
                color = colorResource(
                    id = R.color.brand_primary
                ),
                fontSize = 32.sp
            )

            Text(
                text = "☆",
                color = colorResource(
                    id = R.color.text_secondary
                ),
                fontSize = 32.sp
            )
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // Opinion
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


        // Campo de opinion
        OutlinedTextField(
            value = "",
            onValueChange = {},

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


        // Contador de caracteres
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            Text(
                text = "0/1000",
                color = colorResource(
                    id = R.color.text_secondary
                ),
                fontSize = 11.sp
            )
        }


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // Etiquetas
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


        Spacer(
            modifier = Modifier.height(145.dp)
        )


        // Boton publicar
        AppButton(
            text = stringResource(
                id = R.string.btn_publish_review
            ),
            onClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {

    GamerCornerAppTheme {
        ReviewScreen()
    }
}