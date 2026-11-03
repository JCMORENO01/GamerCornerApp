package com.example.gamercornerapp.data.dto

import com.example.gamercornerapp.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MappersTest {

    @Test
    fun `GameDTO toGame maps valid data correctly`() {
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

        val game = dto.toGame()

        assertEquals("1", game.id)
        assertEquals("Test Game", game.title)
        assertEquals("Test Dev", game.developer)
        assertEquals(2023, game.year)
        assertEquals("https://example.com/image.jpg", game.imageUrl)
        assertEquals("Test Desc", game.description)
        assertEquals(listOf("Action", "RPG"), game.tags)
        assertEquals(4.5, game.rating, 0.0)
        assertEquals(100, game.reviewsCount)
        assertEquals(R.drawable.elden, game.image) // ID "1" maps to elden
    }

    @Test
    fun `GameDTO toGame maps missing data to defaults`() {
        val dto = GameDTO(
            id = "unknown",
            title = "Unknown Game",
            developer = "Unknown Dev",
            year = 0
        )

        val game = dto.toGame()

        assertEquals("unknown", game.id)
        assertEquals(R.drawable.elden, game.image) // Default map for unknown ID
        assertEquals(null, game.imageUrl)
        assertEquals(0.0, game.rating, 0.0)
        assertEquals(0, game.reviewsCount)
        assertTrue(game.tags.isEmpty())
        assertEquals("", game.description)
    }

    @Test
    fun `ReviewDTO toReviewItem maps valid data correctly`() {
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

        val item = dto.toReviewItem()

        assertEquals("r1", item.id)
        assertEquals("Elden", item.gameTitle)
        assertEquals(4, item.rating)
        assertEquals("2023-01-01", item.relativeDate)
        assertEquals(R.drawable.mini_elden, item.gameImageId)
        assertEquals("Great game!", item.description)
        assertEquals(listOf("Fun"), item.tags)
        assertEquals("u1", item.authorId)
        assertEquals("@johndoe", item.authorName)
        assertEquals("http://pic.jpg", item.authorImageUrl)
        assertEquals("http://game.jpg", item.gameImageUrl)
        assertEquals(10, item.likesCount)
        assertTrue(item.isLiked)
    }

    @Test
    fun `ReviewDTO toFeedPost maps missing data to defaults`() {
        val dto = ReviewDTO(
            id = "r2",
            userId = "u2",
            gameId = "unknown"
        )

        val post = dto.toFeedPost()

        assertEquals("r2", post.id)
        assertEquals(0.0, post.rating, 0.0)
        assertEquals("", post.description)
        assertTrue(post.tags.isEmpty())
        assertEquals(0, post.likesCount)
        assertEquals(0, post.commentsCount)

        // Default author mapping
        assertEquals("u2", post.author.id)
        assertEquals("Usuario", post.author.username)
        assertEquals("usuario", post.author.nickName)

        // Default game mapping
        assertEquals("unknown", post.game.id)
        assertEquals("Juego", post.game.title)
        assertEquals(R.drawable.elden, post.game.image) // Default for unknown gameId
    }
}