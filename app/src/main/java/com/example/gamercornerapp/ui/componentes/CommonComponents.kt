package com.example.gamercornerapp.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.theme.LocalIsDarkTheme


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
                color = MaterialTheme.colorScheme.onBackground,
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
                        color = MaterialTheme.colorScheme
                            .onSurfaceVariant
                            .copy(alpha = 0.6f),
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

                focusedBorderColor =
                    MaterialTheme.colorScheme.primary,

                unfocusedBorderColor =
                    MaterialTheme.colorScheme.surface,

                focusedContainerColor =
                    MaterialTheme.colorScheme.surface,

                unfocusedContainerColor =
                    MaterialTheme.colorScheme.surface,

                focusedTextColor =
                    MaterialTheme.colorScheme.onSurface,

                unfocusedTextColor =
                    MaterialTheme.colorScheme.onSurface,

                cursorColor =
                    MaterialTheme.colorScheme.primary
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
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
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
                color = MaterialTheme.colorScheme.onPrimary,
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
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    textColor: Color = Color.Unspecified
) {

    val finalBackgroundColor =
        if (backgroundColor == Color.Unspecified) {
            MaterialTheme.colorScheme.surface
        } else {
            backgroundColor
        }


    val finalTextColor =
        if (textColor == Color.Unspecified) {
            MaterialTheme.colorScheme.onSurface
        } else {
            textColor
        }


    Box(
        modifier = modifier
            .height(36.dp)
            .background(
                finalBackgroundColor,
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 14.dp),

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            color = finalTextColor,
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
                        MaterialTheme.colorScheme.surface,
                        CircleShape
                    )
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            Column {

                Text(
                    text = name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )


                Spacer(
                    modifier = Modifier.height(2.dp)
                )


                Text(
                    text = handle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                    containerColor = MaterialTheme.colorScheme.surface
                ),

                contentPadding = PaddingValues(0.dp)
            ) {

                Text(
                    text = stringResource(
                        id = R.string.btn_following
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 12.sp
                )
            }

        } else {

            val gradientButton = Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
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
                        text = stringResource(
                            id = R.string.btn_follow
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


// Campo de contraseña reutilizable
@Composable
fun AppPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    showPassword: Boolean,
    onShowPasswordChange: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,

            placeholder = {

                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            },

            trailingIcon = {

                IconButton(
                    onClick = onShowPasswordChange
                ) {

                    Icon(
                        imageVector = if (showPassword) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },

                        contentDescription = null,

                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },

            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),

            shape = RoundedCornerShape(16.dp),

            singleLine = true,

            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor =
                    MaterialTheme.colorScheme.primary,

                unfocusedBorderColor =
                    MaterialTheme.colorScheme.surface,

                focusedContainerColor =
                    MaterialTheme.colorScheme.surface,

                unfocusedContainerColor =
                    MaterialTheme.colorScheme.surface,

                focusedTextColor =
                    MaterialTheme.colorScheme.onSurface,

                unfocusedTextColor =
                    MaterialTheme.colorScheme.onSurface,

                cursorColor =
                    MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
fun LoginLogo () {
    val isDark = LocalIsDarkTheme.current
    val logoRes = if (isDark) {
        R.drawable.logo_gamer1      // Logo blanco para modo oscuro
    } else {
        R.drawable.logo_fondo_claro // Logo negro para modo claro
    }

    Image(
        painter = painterResource(
            id = logoRes
        ),
        contentDescription = stringResource(
            id = R.string.logo_content_description
        ),
        modifier = Modifier
            .width(320.dp) // Ajustado para ser más consistente
            .height(140.dp)
    )
}