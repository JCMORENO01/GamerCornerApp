package com.example.gamercornerapp.ui.Screens.startpage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun StartApp(
    modifier: Modifier = Modifier
) {

    StartAppContent(
        modifier = modifier
    )
}


@Composable
fun StartAppContent(
    modifier: Modifier = Modifier
) {

    val gradientButton = Brush.horizontalGradient(
        colors = listOf(
            colorResource(id = R.color.brand_primary),
            colorResource(id = R.color.brand_purple)
        )
    )


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.brand_background)
            )
            .padding(
                horizontal = 28.dp,
                vertical = 28.dp
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(
                modifier = Modifier.height(80.dp)
            )


            // Logo
            Image(
                painter = painterResource(
                    id = R.drawable.logo_gamer
                ),
                contentDescription = stringResource(id = R.string.logo_content_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // Frase principal
            Text(
                text = stringResource(id = R.string.start_title),
                color = colorResource(
                    id = R.color.white
                ),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // Descripcion
            Text(
                text = stringResource(id = R.string.start_description),
                color = colorResource(
                    id = R.color.text_secondary
                ),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )


            Spacer(
                modifier = Modifier.weight(1f)
            )


            // Indicadores
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            colorResource(
                                id = R.color.brand_primary
                            ),
                            CircleShape
                        )
                )

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            colorResource(
                                id = R.color.card_background
                            ),
                            CircleShape
                        )
                )

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            colorResource(
                                id = R.color.card_background
                            ),
                            CircleShape
                        )
                )
            }


            Spacer(
                modifier = Modifier.height(48.dp)
            )


            // Boton continuar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .background(
                        brush = gradientButton,
                        shape = RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = stringResource(id = R.string.btn_continue),
                        color = colorResource(
                            id = R.color.white
                        ),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Text(
                        text = "›",
                        color = colorResource(
                            id = R.color.white
                        ),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(28.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StartAppPreview() {

    GamerCornerAppTheme {
        StartApp()
    }
}