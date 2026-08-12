package com.example.gamercornerapp.ui.componentes


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R

@Composable
fun GamerBottomBar() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                colorResource(id = R.color.brand_background)
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🏠",
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = stringResource(
                        id = R.string.nav_home
                    ),
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 10.sp
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🧭",
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = stringResource(
                        id = R.string.nav_explore
                    ),
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 10.sp
                )
            }


            Spacer(
                modifier = Modifier.width(40.dp)
            )


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🔔",
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = stringResource(
                        id = R.string.nav_notifications
                    ),
                    color = colorResource(
                        id = R.color.text_secondary
                    ),
                    fontSize = 10.sp
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "👤",
                    color = colorResource(
                        id = R.color.brand_primary
                    ),
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = stringResource(
                        id = R.string.nav_profile
                    ),
                    color = colorResource(
                        id = R.color.brand_primary
                    ),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        // Botón +
        val gradientAdd = Brush.verticalGradient(
            colors = listOf(
                colorResource(
                    id = R.color.brand_primary
                ),
                colorResource(
                    id = R.color.brand_purple
                )
            )
        )

        Box(
            modifier = Modifier
                .size(52.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-10).dp)
                .background(
                    gradientAdd,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "+",
                color = colorResource(
                    id = R.color.white
                ),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}