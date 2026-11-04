package com.example.gamercornerapp.data.repository

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
class UserRepositoryIntegrationTest {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var userRemoteDataSource: UserFirestoreDataSourceImpl
    private lateinit var userRepository: UserRepository
    private lateinit var testUserId: String

    @Before
    fun setUp() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        try {
            db.useEmulator("10.0.2.2", 8080)
            auth.useEmulator("10.0.2.2", 9099)
        } catch (_: Exception) {}

        userRemoteDataSource = UserFirestoreDataSourceImpl(db, auth)
        userRepository = UserRepository(userRemoteDataSource)

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
            "user_repo_1",
            "user_repo_3",
            "user_repo_4",
            "repo_follower",
            "repo_target",
            "repo_target_2",
            "repo_follower_2",
            testUserId
        )

        try {
            for (id in users) {
                val userRef = db.collection("users").document(id)

                // Eliminar relaciones de seguimiento
                for (collection in listOf("following", "followers")) {
                    while (true) {
                        val documents = userRef.collection(collection)
                            .limit(100)
                            .get()
                            .await()
                            .documents

                        if (documents.isEmpty()) break

                        for (doc in documents) {
                            doc.reference.delete().await()
                        }
                    }
                }

                // Eliminar documento del usuario
                userRef.delete().await()
            }
        } finally {
            try {
                // Eliminar cuenta anónima de prueba
                if (auth.currentUser?.isAnonymous == true) {
                    auth.currentUser?.delete()?.await()
                }
            } finally {
                auth.signOut()
            }
        }
    }

    private fun createTestUser(id: String, username: String, nickName: String = "@$username"): UserProfileDTO {
        return UserProfileDTO(
            id = id,
            username = username,
            nickName = nickName.lowercase(),
            bio = "Biografía de prueba",
            birthDate = "2000-01-01",
            profileBackgroundId = 0,
            profileBgDescription = "Fondo",
            profileImageId = 0,
            profilePictureUrl = "https://example.com/avatar/$id.png",
            followersCount = 10L,
            followingCount = 5L,
            FCM_token = "token_fcm_$id",
            isFollowed = false
        )
    }

    // 1. Obtener usuario valido (Exito)
    @Test
    fun getUserById_validId_returnsSuccess() = runTest {
        //arrange
        val user = createTestUser(id = "user_repo_1", username = "Repo User")
        db.collection("users").document(user.id).set(user).await()

        //act
        val result = userRepository.getUserById("user_repo_1")

        //assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.username).isEqualTo("Repo User")
    }

    // 2. Obtener usuario invalido (Fallo)
    @Test
    fun getUserById_invalidId_returnsFailure() = runTest {
        //arrange
        val invalidId = "user_999"

        //act
        val result = userRepository.getUserById(invalidId)

        //assert
        assertThat(result.isFailure).isTrue()
    }

    // 3. Crear perfil de usuario
    @Test
    fun createUserProfile_success() = runTest {
        //arrange
        val id = "user_repo_3"

        //act
        val result = userRepository.createUserProfile(
            id = id,
            username = "New Repo User",
            nickName = "@newrepo"
        )

        //assert
        assertThat(result.isSuccess).isTrue()

        val doc = db.collection("users").document(id).get().await()
        assertThat(doc.exists()).isTrue()
    }

    // 4. Buscar usuarios retorna resultados
    @Test
    fun searchUsers_returnsSuccessWithResults() = runTest {
        //arrange
        val user = createTestUser(id = "user_repo_4", username = "UniqueName", nickName = "uniquename")
        db.collection("users").document(user.id).set(user).await()

        //act
        val result = userRepository.searchUsers("unique")

        //assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.isNotEmpty()).isTrue()
    }

    // 5. Toggle follow user (Exito)
    @Test
    fun toggleFollowUser_returnsSuccess() = runTest {
        //arrange
        val currentUserId = testUserId
        val targetUserId = "repo_target"

        val currentUser = createTestUser(id = currentUserId, username = "Current User")
        val targetUser = createTestUser(id = targetUserId, username = "Target User")
        db.collection("users").document(currentUserId).set(currentUser).await()
        db.collection("users").document(targetUserId).set(targetUser).await()

        //act
        val result = userRepository.toggleFollowUser(currentUserId, targetUserId)

        //assert
        assertThat(result.isSuccess).isTrue()
    }

    // 6. Obtener lista de seguidores
    @Test
    fun getFollowers_returnsSuccessWithList() = runTest {
        //arrange
        val targetId = "repo_target_2"
        val targetUser = createTestUser(id = targetId, username = "Target 2")
        db.collection("users").document(targetId).set(targetUser).await()

        //act
        val result = userRepository.getFollowers(targetId)

        //assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isNotNull()
    }

    // 7. Obtener lista de seguidos
    @Test
    fun getFollowing_returnsSuccessWithList() = runTest {
        //arrange
        val followerId = "repo_follower_2"
        val followerUser = createTestUser(id = followerId, username = "Follower 2")
        db.collection("users").document(followerId).set(followerUser).await()

        //act
        val result = userRepository.getFollowing(followerId)

        //assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isNotNull()
    }

    // 8. Actualizar perfil de usuario
    @Test
    fun updateUserProfile_returnsSuccess() = runTest {
        //arrange
        val user = createTestUser(id = testUserId, username = "Old Repo")
        db.collection("users").document(user.id).set(user).await()

        //act
        val result = userRepository.updateUserProfile(
            id = user.id,
            username = "New Repo",
            bio = "New Bio",
            birthDate = null,
            profilePictureUrl = null
        )

        //assert
        assertThat(result.isSuccess).isTrue()

        val updatedDoc = db.collection("users").document(user.id).get().await()
        assertThat(updatedDoc.getString("username")).isEqualTo("New Repo")
    }
}