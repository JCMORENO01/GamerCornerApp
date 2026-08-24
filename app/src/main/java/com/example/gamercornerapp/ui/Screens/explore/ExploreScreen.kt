package com.example.gamercornerapp.ui.Screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.ui.Screens.explore.components.CategoryChipsSection
import com.example.gamercornerapp.ui.Screens.explore.components.ExploreSearchBar
import com.example.gamercornerapp.ui.Screens.explore.components.ExploreTopBar
import com.example.gamercornerapp.ui.Screens.explore.components.PopularGamesSection
import com.example.gamercornerapp.ui.Screens.explore.components.ResultGameCard
import com.example.gamercornerapp.ui.Screens.explore.components.ResultsHeader
import com.example.gamercornerapp.ui.model.Game
import com.example.gamercornerapp.ui.model.GameCategory
import com.example.gamercornerapp.ui.model.local.LocalDataProvider
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme


@Composable
fun ExploreScreen(
    popularGames: List<Game>,
    categories: List<GameCategory>,
    resultGames: List<Game>,
    modifier: Modifier = Modifier
) {

    // Estado de busqueda
    var searchQuery by remember {
        mutableStateOf("")
    }

    // Estado de la categoria seleccionada
    var selectedCategory by remember {
        mutableStateOf(categories.firstOrNull()?.name ?: "")
    }

    // Estado de los juegos marcados como favoritos (por titulo)
    var favoriteGameTitles by remember {
        mutableStateOf(setOf<String>())
    }


    ExploreScreenContent(
        popularGames = popularGames,
        categories = categories,
        resultGames = resultGames,

        searchQuery = searchQuery,
        onSearchQueryChange = {
            searchQuery = it
        },

        selectedCategory = selectedCategory,
        onCategorySelected = {
            selectedCategory = it
        },

        favoriteGameTitles = favoriteGameTitles,
        onFavoriteClick = { game ->

            favoriteGameTitles = if (
                favoriteGameTitles.contains(game.title)
            ) {
                favoriteGameTitles - game.title
            } else {
                favoriteGameTitles + game.title
            }
        },

        modifier = modifier
    )
}


@Composable
fun ExploreScreenContent(
    popularGames: List<Game>,
    categories: List<GameCategory>,
    resultGames: List<Game>,

    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,

    selectedCategory: String,
    onCategorySelected: (String) -> Unit,

    favoriteGameTitles: Set<String>,
    onFavoriteClick: (Game) -> Unit,

    modifier: Modifier = Modifier,
    onFiltersClick: () -> Unit = { },
    onSeeAllCategoriesClick: () -> Unit = { },
    onAddFiltersClick: () -> Unit = { },
    onPopularGameClick: (Game) -> Unit = { },
    onResultGameClick: (Game) -> Unit = { }
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),

        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            ),

        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),

        horizontalArrangement = Arrangement.spacedBy(12.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Encabezado
        item(span = { GridItemSpan(maxLineSpan) }) {

            ExploreTopBar(
                onFiltersClick = onFiltersClick
            )
        }


        // Barra de busqueda
        item(span = { GridItemSpan(maxLineSpan) }) {

            ExploreSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }


        // Populares esta semana
        item(span = { GridItemSpan(maxLineSpan) }) {

            PopularGamesSection(
                games = popularGames,
                onGameClick = onPopularGameClick,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }


        // Categorias
        item(span = { GridItemSpan(maxLineSpan) }) {

            CategoryChipsSection(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected,
                onSeeAllClick = onSeeAllCategoriesClick,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }


        // Encabezado de resultados
        item(span = { GridItemSpan(maxLineSpan) }) {

            ResultsHeader(
                onAddFiltersClick = onAddFiltersClick,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }


        // Cuadricula de resultados
        items(
            items = resultGames,
            key = { it.title }
        ) { game ->

            ResultGameCard(
                game = game,
                isFavorite = favoriteGameTitles.contains(game.title),
                onClick = {
                    onResultGameClick(game)
                },
                onFavoriteClick = {
                    onFavoriteClick(game)
                }
            )
        }
    }
}


@Preview(
    showBackground = true,
    name = "Explore Dark"
)
@Composable
fun ExploreScreenPreview() {

    GamerCornerAppTheme(
        darkTheme = true
    ) {

        ExploreScreen(
            popularGames = LocalDataProvider.popularGames,
            categories = LocalDataProvider.exploreCategories,
            resultGames = LocalDataProvider.exploreResults
        )
    }
}
