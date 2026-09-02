package com.example.gamercornerapp.ui.Screens.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.Game
import com.example.gamercornerapp.data.local.LocalDataProvider
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun PopularGamesSection(
    games: List<Game>,
    modifier: Modifier = Modifier,
    onGameClick: (Game) -> Unit = { }
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(
                id = R.string.title_popular_this_week
            ).uppercase(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            games.forEachIndexed { index, game ->

                PopularGameItem(
                    rank = index + 1,
                    game = game,
                    onClick = {
                        onGameClick(game)
                    }
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Popular Games Section Dark"
)
@Composable
fun PopularGamesSectionPreview() {

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

            PopularGamesSection(
                games = LocalDataProvider.popularGames
            )
        }
    }
}
