package com.example.gamercornerapp.data.dto

import com.example.gamercornerapp.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MappersTest {

    @Test
    fun gameDTO_toGame_mapsValidDataCorrectly() {
        //AAA
        //arrange
        val dto = GameDTO(
            id = "1",
            title = "Test Game",
            developer = "Test Dev",
            year = 2023,
            image = "https://example.com/image.jpg",
            description = "Test Desc",
            tags = listOf("Action", "RPG"),
            rating = 4.5,
            reviewsCount = 100
        )

        //act
        val game = dto.toGame()

        //assert
        assertThat(game.id).isEqualTo("1")
        assertThat(game.title).isEqualTo("Test Game")
        assertThat(game.developer).isEqualTo("Test Dev")
        assertThat(game.year).isEqualTo(2023)
        assertThat(game.imageUrl).isEqualTo("https://example.com/image.jpg")
        assertThat(game.description).isEqualTo("Test Desc")
        assertThat(game.tags).containsExactly("Action", "RPG").inOrder()
        assertThat(game.rating).isEqualTo(4.5)
        assertThat(game.reviewsCount).isEqualTo(100)
        assertThat(game.image).isEqualTo(R.drawable.elden)
    }

    @Test
    fun gameDTO_toGame_mapsMissingDataToDefaults() {
        //AAA
        //arrange
        val dto = GameDTO(
            id = "unknown",
            title = "Unknown Game",
            developer = "Unknown Dev",
            year = 0
        )

        //act
        val game = dto.toGame()

        //assert
        assertThat(game.id).isEqualTo("unknown")
        assertThat(game.image).isEqualTo(R.drawable.elden)
        assertThat(game.imageUrl).isNull()
        assertThat(game.rating).isEqualTo(0.0)
        assertThat(game.reviewsCount).isEqualTo(0)
        assertThat(game.tags).isEmpty()
        assertThat(game.description).isEmpty()
    }

    @Test
    fun reviewDTO_toReviewItem_mapsValidDataCorrectly() {
        //AAA
        //arrange
        val dto = ReviewDTO(
            id = "r1",
            description = "Great game!",
            rating = 4.5f,
            tags = listOf("Fun"),
            userId = "u1",
            gameId = "1",
            createdAt = "2023-01-01",
            user = CreateReviewUserDTO(username = "John", nickName = "johndoe", profilePictureUrl = "http://pic.jpg"),
            game = CreateReviewGameDTO(title = "Elden", image = "http://game.jpg"),
            likesCount = 10L,
            isLiked = true,
            isBookmarked = false
        )

        //act
        val item = dto.toReviewItem()

        //assert
        assertThat(item.id).isEqualTo("r1")
        assertThat(item.gameTitle).isEqualTo("Elden")
        assertThat(item.rating).isEqualTo(4)
        assertThat(item.relativeDate).isEqualTo("2023-01-01")
        assertThat(item.gameImageId).isEqualTo(R.drawable.mini_elden)
        assertThat(item.description).isEqualTo("Great game!")
        assertThat(item.tags).containsExactly("Fun")
        assertThat(item.authorId).isEqualTo("u1")
        assertThat(item.authorName).isEqualTo("@johndoe")
        assertThat(item.authorImageUrl).isEqualTo("http://pic.jpg")
        assertThat(item.gameImageUrl).isEqualTo("http://game.jpg")
        assertThat(item.likesCount).isEqualTo(10)
        assertThat(item.isLiked).isTrue()
    }

    @Test
    fun reviewDTO_toFeedPost_mapsMissingDataToDefaults() {
        //AAA
        //arrange
        val dto = ReviewDTO(
            id = "r2",
            userId = "u2",
            gameId = "unknown"
        )

        //act
        val post = dto.toFeedPost()

        //assert
        assertThat(post.id).isEqualTo("r2")
        assertThat(post.rating).isEqualTo(0.0)
        assertThat(post.description).isEmpty()
        assertThat(post.tags).isEmpty()
        assertThat(post.likesCount).isEqualTo(0)
        assertThat(post.commentsCount).isEqualTo(0)

        assertThat(post.author.id).isEqualTo("u2")
        assertThat(post.author.username).isEqualTo("Usuario")
        assertThat(post.author.nickName).isEqualTo("usuario")

        assertThat(post.game.id).isEqualTo("unknown")
        assertThat(post.game.title).isEqualTo("Juego")
        assertThat(post.game.image).isEqualTo(R.drawable.elden)
    }
}
