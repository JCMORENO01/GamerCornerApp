package com.example.gamercornerapp.data.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gamercornerapp.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class FirebaseUserDataSourceTest {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var dataSource: UserFirestoreDataSourceImpl
    private lateinit var testUserId: String

    @Before
    fun setUp() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        try {
            db.useEmulator("10.0.2.2", 8080)
            auth.useEmulator("10.0.2.2", 9099)
        } catch (_: Exception) {}

        dataSource = UserFirestoreDataSourceImpl(db, auth)

        // Autenticar un usuario de prueba en el emulador para que los métodos de DataSource no lancen "Usuario no autenticado"
        runBlocking {
            try {
                if (auth.currentUser == null) {
                    auth.signInAnonymously().await()
                }
                testUserId = auth.currentUser?.uid ?: UUID.randomUUID().toString()
            } catch (_: Exception) {
                testUserId = UUID.randomUUID().toString()
            }
        }
    }

    @After
    fun tearDown() = runBlocking {
        val users = listOf(
            "user_test_1",
            "user_test_3",
            "search_1",
            "search_2",
            "search_3",
            "target_1",
            "target_2",
            "target_3",
            testUserId
        )

        for (id in users) {
            val userRef = db.collection("users").document(id)

            // Eliminar documentos de todas las subcolecciones conocidas
            for (name in listOf("following", "followers")) {
                val docs = userRef.collection(name).get().await()

                for (doc in docs.documents) {
                    doc.reference.delete().await()
                }
            }

            // Eliminar el documento principal
            userRef.delete().await()
        }

        // Cerrar sesión
        auth.signOut()
    }

    private fun createTestUser(id: String, username: String, nickName: String = "@$username"): UserProfileDTO {
        return UserProfileDTO(
            id = id,
            username = username,
            nickName = nickName.lowercase(),
            bio = "Biografía de prueba para $username",
            birthDate = "2000-01-01",
            profileBackgroundId = 0,
            profileBgDescription = "Fondo de $username",
            profileImageId = 0,
            profilePictureUrl = "https://example.com/avatar/$id.png",
            followersCount = 10L,
            followingCount = 5L,
            FCM_token = "token_fcm_$id",
            isFollowed = false
        )
    }

    // 1. Obtener usuario existente
    @Test
    fun getUserById_validId_correctUser() = runTest {
        val user = createTestUser(id = "user_test_1", username = "Juan")
        db.collection("users").document(user.id).set(user).await()

        val result = dataSource.getUserById(user.id)
        assertThat(result).isNotNull()
        assertThat(result.username).isEqualTo("Juan")
        assertThat(result.bio).isEqualTo("Biografía de prueba para Juan")
        assertThat(result.FCM_token).isEqualTo("token_fcm_user_test_1")
    }

    // 2. Obtener usuario no existente lanza excepcion
    @Test(expected = Exception::class)
    fun getUserById_invalidId_throwsException() = runTest {
        dataSource.getUserById("invalid_id")
    }

    // 3. Registrar nuevo usuario
    @Test
    fun registerUser_createsNewDocument() = runTest {
        val user = createTestUser(id = "user_test_3", username = "Carlos")
        dataSource.registerUser(user, user.id)

        val doc = db.collection("users").document(user.id).get().await()
        assertThat(doc.exists()).isTrue()
        assertThat(doc.getString("username")).isEqualTo("Carlos")
        assertThat(doc.getString("profilePictureUrl")).isEqualTo("https://example.com/avatar/user_test_3.png")
    }

    // 4. Actualizar usuario existente
    @Test
    fun updateUserProfile_updatesFieldsCorrectly() = runTest {
        val user = createTestUser(id = testUserId, username = "Old Name")
        db.collection("users").document(testUserId).set(user).await()

        val updatedUser = user.copy(username = "New Name", bio = "New Bio Updated")

        // Ahora usamos directamente dataSource porque testUserId == auth.currentUser.uid
        dataSource.updateUserProfile(testUserId, updatedUser)

        val doc = db.collection("users").document(testUserId).get().await()
        assertThat(doc.getString("username")).isEqualTo("New Name")
        assertThat(doc.getString("bio")).isEqualTo("New Bio Updated")
    }

    // 5. Buscar usuarios (Query)
    @Test
    fun searchUsers_returnsMatchingUsers() = runTest {
        val user1 = createTestUser(id = "search_1", username = "GamerOne", nickName = "@gamerone")
        val user2 = createTestUser(id = "search_2", username = "GamerTwo", nickName = "@gamertwo")
        val user3 = createTestUser(id = "search_3", username = "Player", nickName = "@player")

        db.collection("users").document(user1.id).set(user1).await()
        db.collection("users").document(user2.id).set(user2).await()
        db.collection("users").document(user3.id).set(user3).await()

        val results = dataSource.searchUsers("@gamer")
        assertThat(results.size).isAtLeast(2)
        assertThat(results.first().bio).isNotEmpty()
    }

    // 6. Seguir a un usuario
    @Test
    fun toggleFollowUser_addsToFollowing() = runTest {
        val currentUserId = testUserId
        val targetUserId = "target_1"

        // Creamos documentos de usuario completos
        val currentUser = createTestUser(id = currentUserId, username = "Current User")
        val targetUser = createTestUser(id = targetUserId, username = "Target User 1")
        db.collection("users").document(currentUserId).set(currentUser).await()
        db.collection("users").document(targetUserId).set(targetUser).await()

        dataSource.toggleFollowUser(currentUserId, targetUserId)

        val isFollowing = dataSource.isFollowing(currentUserId, targetUserId)
        assertThat(isFollowing).isTrue()
    }

    // 7. Dejar de seguir a un usuario
    @Test
    fun toggleFollowUser_twice_removesFromFollowing() = runTest {
        val currentUserId = testUserId
        val targetUserId = "target_2"

        val currentUser = createTestUser(id = currentUserId, username = "Current User")
        val targetUser = createTestUser(id = targetUserId, username = "Target User 2")
        db.collection("users").document(currentUserId).set(currentUser).await()
        db.collection("users").document(targetUserId).set(targetUser).await()

        dataSource.toggleFollowUser(currentUserId, targetUserId) // Follow
        dataSource.toggleFollowUser(currentUserId, targetUserId) // Unfollow

        val isFollowing = dataSource.isFollowing(currentUserId, targetUserId)
        assertThat(isFollowing).isFalse()
    }

    // 8. Obtener lista de seguidores
    @Test
    fun getFollowers_returnsCorrectIds() = runTest {
        val currentUserId = "target_3"
        val followerId = testUserId

        val currentUser = createTestUser(id = currentUserId, username = "Target User 3")
        val followerUser = createTestUser(id = followerId, username = "Follower User")
        db.collection("users").document(currentUserId).set(currentUser).await()
        db.collection("users").document(followerId).set(followerUser).await()

        // Follower sigue a target
        dataSource.toggleFollowUser(followerId, currentUserId)

        val followers = dataSource.getFollowers(currentUserId)
        assertThat(followers).contains(followerId)
    }
}