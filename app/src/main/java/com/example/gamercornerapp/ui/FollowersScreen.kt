package com.example.gamercornerapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.componentes.UserRowItem
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme

data class UserProfile(
    val name: String,
    val handle: String,
    val isFollowing: Boolean
)

@Composable
fun FollowersScreen(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableStateOf(0) }

    val usersList = listOf(
        UserProfile("Drakool", "@drakool_gamer", false),
        UserProfile("LadyAki", "@ladyaki_x", true),
        UserProfile("ShadowX", "@shadowx_99", false),
        UserProfile("NeoGamer", "@neogamer_07", false),
        UserProfile("PixelPro", "@pixelpro_gaming", false),
        UserProfile("RogueMaster", "@roguemaster", false)
    )

    Scaffold(
        containerColor = colorResource(id = R.color.brand_background),
        bottomBar = {
            GamerBottomBar()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "‹",
                    color = colorResource(id = R.color.white),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = stringResource(id = R.string.title_followers),
                    color = colorResource(id = R.color.white),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(colorResource(id = R.color.card_background), RoundedCornerShape(24.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TabButton(
                    text = stringResource(id = R.string.tab_followers),
                    isSelected = selectedTab == 0,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedTab = 0 }
                )
                TabButton(
                    text = stringResource(id = R.string.tab_following),
                    isSelected = selectedTab == 1,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedTab = 1 }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(usersList) { user ->
                    UserRowItem(
                        name = user.name,
                        handle = user.handle,
                        isFollowing = user.isFollowing,
                        onFollowClick = { }
                    )
                }
            }
        }
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) colorResource(id = R.color.brand_background) else Color.Transparent
    val textColor = if (isSelected) colorResource(id = R.color.brand_primary) else colorResource(id = R.color.text_secondary)

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        TextButton(onClick = onClick) {
            Text(
                text = text,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GamerBottomBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        color = colorResource(id = R.color.brand_background)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🏠", color = colorResource(id = R.color.text_secondary), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = stringResource(id = R.string.nav_home), color = colorResource(id = R.color.text_secondary), fontSize = 10.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🧭", color = colorResource(id = R.color.text_secondary), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = stringResource(id = R.string.nav_explore), color = colorResource(id = R.color.text_secondary), fontSize = 10.sp)
                }

                Spacer(modifier = Modifier.width(40.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🔔", color = colorResource(id = R.color.text_secondary), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = stringResource(id = R.string.nav_notifications), color = colorResource(id = R.color.text_secondary), fontSize = 10.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "👤", color = colorResource(id = R.color.brand_primary), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = stringResource(id = R.string.nav_profile), color = colorResource(id = R.color.brand_primary), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            val gradientAdd = Brush.verticalGradient(
                colors = listOf(
                    colorResource(id = R.color.brand_primary),
                    colorResource(id = R.color.brand_purple)
                )
            )
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = (-10).dp)
                    .background(gradientAdd, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    color = colorResource(id = R.color.white),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FollowersScreenPreview() {
    GamerCornerAppTheme {
        FollowersScreen()
    }
}