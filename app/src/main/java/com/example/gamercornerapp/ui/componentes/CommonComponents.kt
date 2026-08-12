package com.example.gamercornerapp.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R


// Campo de texto reutilizable
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        if (label.isNotEmpty()) {

            Text(
                text = label,
                color = colorResource(id = R.color.white),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    bottom = 4.dp,
                    start = 4.dp
                )
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,

            placeholder = {
                if (placeholder.isNotEmpty()) {
                    Text(
                        text = placeholder,
                        color = colorResource(
                            id = R.color.text_secondary
                        ).copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),

            shape = RoundedCornerShape(16.dp),

            singleLine = true,

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
    }
}


// Botón principal
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val gradientButton = Brush.horizontalGradient(
        colors = listOf(
            colorResource(id = R.color.brand_primary),
            colorResource(id = R.color.brand_purple)
        )
    )

    Button(
        onClick = onClick,

        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),

        shape = RoundedCornerShape(25.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),

        contentPadding = PaddingValues(0.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    gradientButton,
                    RoundedCornerShape(25.dp)
                ),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = text,
                color = colorResource(id = R.color.white),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// Chip
@Composable
fun AppChip(
    text: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .height(36.dp)
            .background(
                colorResource(id = R.color.card_background),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 14.dp),

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            color = colorResource(id = R.color.white),
            fontSize = 13.sp
        )
    }
}


// Elemento de usuario
@Composable
fun UserRowItem(
    name: String,
    handle: String,
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // Información del usuario
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {

            // Foto de perfil
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        colorResource(
                            id = R.color.card_background
                        ),
                        CircleShape
                    )
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = name,
                    color = colorResource(
                        id = R.color.white
                    ),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = handle,
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(
            modifier = Modifier.width(8.dp)
        )


        // Botón seguir
        if (isFollowing) {

            Button(
                onClick = onFollowClick,

                modifier = Modifier
                    .width(110.dp)
                    .height(36.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(
                        id = R.color.card_background
                    )
                ),

                contentPadding = PaddingValues(0.dp)
            ) {

                Text(
                    text = "Siguiendo",
                    color = colorResource(
                        id = R.color.white
                    ),
                    fontSize = 12.sp
                )
            }

        } else {

            val gradientButton = Brush.horizontalGradient(
                colors = listOf(
                    colorResource(
                        id = R.color.brand_primary
                    ),
                    colorResource(
                        id = R.color.brand_purple
                    )
                )
            )

            Button(
                onClick = onFollowClick,

                modifier = Modifier
                    .width(110.dp)
                    .height(36.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),

                contentPadding = PaddingValues(0.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            gradientButton,
                            RoundedCornerShape(18.dp)
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "Seguir",
                        color = colorResource(
                            id = R.color.white
                        ),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}