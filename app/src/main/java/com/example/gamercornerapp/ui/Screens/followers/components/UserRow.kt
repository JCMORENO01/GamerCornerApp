package com.example.gamercornerapp.ui.Screens.followers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R


@Composable
fun UserRow(
    name: String,
    handle: String,
    image: Int,
    isFollowing: Boolean
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Imagen y informacion del usuario
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = image),
                contentDescription = stringResource(id = R.string.user_profile_photo_description, name),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = name,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = handle,
                    color = colorResource(id = R.color.text_secondary),
                    fontSize = 14.sp
                )
            }
        }


        // Boton seguir
        Box(
            modifier = Modifier
                .background(
                    color = if (isFollowing) {
                        colorResource(id = R.color.card_background)
                    } else {
                        colorResource(id = R.color.brand_primary)
                    },
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(
                    horizontal = 18.dp,
                    vertical = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = if (isFollowing) {
                    stringResource(id = R.string.btn_following)
                } else {
                    stringResource(id = R.string.btn_follow)
                },
                color = colorResource(id = R.color.white),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}