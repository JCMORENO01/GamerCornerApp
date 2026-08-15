package com.example.gamercornerapp.ui.Screens.selfProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.R
import com.example.gamercornerapp.ui.model.UserProfile
import com.example.gamercornerapp.ui.model.UserStats
import com.example.gamercornerapp.ui.theme.GamerCornerAppTheme
import androidx.compose.ui.res.colorResource
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileHeaderSection
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileReviewsSection
import com.example.gamercornerapp.ui.Screens.selfProfile.components.ProfileStatsSection
import com.example.gamercornerapp.ui.componentes.GamerBottomBar
import com.example.gamercornerapp.ui.model.ReviewItem

@Composable
fun SelfProfileScreen (
    userProfile: UserProfile,
    reviews: List<ReviewItem>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.brand_background)
            )
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 0.dp
            )
    ) {
        Column() {
            SelfProfileScreenContent(userProfile, reviews)
            Spacer(modifier = Modifier.height(35.dp))
            GamerBottomBar()
        }
    }
}





@Composable
fun SelfProfileScreenContent (
    userProfile: UserProfile,
    reviews: List<ReviewItem>,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        ProfileHeaderSection(userProfile)
        Spacer(modifier = Modifier.height(20.dp))
        ProfileStatsSection(userProfile.stats)
        Spacer(modifier = Modifier.height(30.dp))
        ProfileReviewsSection(reviews)
    }
}




@Preview()
@Composable
fun SelfProfileScreenPreview (){
    GamerCornerAppTheme() {
        SelfProfileScreen(
            userProfile = UserProfile (
                username = "NightHunter",
                nickName = "@nighthunter_21",
                bio = "Vivo para los videojuegos 🎮",
                profileBackgroundId = R.drawable.background_maquinitas,
                profileBgDescription = "Imagen de monitores",
                profileImageId = R.drawable.messi1,
                stats = UserStats(
                    reviewsCount = 128,
                    followersCount = 342,
                    followingCount = 176
                )
            ),

            listOf(
                ReviewItem(
                    id = "1",
                    gameTitle = "Elden Ring",
                    rating = 5,
                    relativeDate = "Hace 2 días",
                    gameImageId = R.drawable.mini_elden,
                    description = "Un juego increible..."
                ),
                ReviewItem(
                    id = "2",
                    gameTitle = "Cyberpunk",
                    rating = 4,
                    relativeDate = "Hace 1 semana",
                    gameImageId = R.drawable.cyberpunk,
                    description = "Una gran historia..."
                )
            )
        )
    }
}
