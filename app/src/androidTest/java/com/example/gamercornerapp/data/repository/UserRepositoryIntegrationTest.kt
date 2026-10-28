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
    fun tearDown() {
        // Nada que limpiar manualmente en este caso
    }

    // 1. Obtener usuario valido (Exito)
    @Test
    fun getUserById_validId_returnsSuccess() = runTest {
        val user = UserProfileDTO(id = "user_repo_1", username = "Repo User")
        db.collection("users").document(user.id).set(user).await()

        val result = userRepository.getUserById("user_repo_1")

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.username).isEqualTo("Repo User")
    }

    // 2. Obtener usuario invalido (Fallo)
    @Test
    fun getUserById_invalidId_returnsFailure() = runTest {
        val result = userRepository.getUserById("user_999")

        assertThat(result.isFailure).isTrue()
    }

    // 3. Crear perfil de usuario
    @Test
    fun createUserProfile_success() = runTest {
        val result = userRepository.createUserProfile(
            id = "user_repo_3",
            username = "New Repo User",
            nickName = "@newrepo"
        )

        assertThat(result.isSuccess).isTrue()
        
        val doc = db.collection("users").document("user_repo_3").get().await()
        assertThat(doc.exists()).isTrue()
    }

    // 4. Buscar usuarios retorna resultados
    @Test
    fun searchUsers_returnsSuccessWithResults() = runTest {
        val user = UserProfileDTO(id = "user_repo_4", username = "UniqueName", nickName = "uniquename")
        db.collection("users").document(user.id).set(user).await()

        val result = userRepository.searchUsers("unique")
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.isNotEmpty()).isTrue()
    }

    // 5. Toggle follow user (Exito)
    @Test
    fun toggleFollowUser_returnsSuccess() = runTest {
        val currentUserId = testUserId
        val targetUserId = "repo_target"
        
        db.collection("users").document(currentUserId).set(mapOf("id" to currentUserId)).await()
        db.collection("users").document(targetUserId).set(mapOf("id" to targetUserId)).await()

        val result = userRepository.toggleFollowUser(currentUserId, targetUserId)
        assertThat(result.isSuccess).isTrue()
    }

    // 6. Obtener lista de seguidores
    @Test
    fun getFollowers_returnsSuccessWithList() = runTest {
        val targetId = "repo_target_2"
        db.collection("users").document(targetId).set(mapOf("id" to targetId)).await()
        
        val result = userRepository.getFollowers(targetId)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isNotNull()
    }

    // 7. Obtener lista de seguidos
    @Test
    fun getFollowing_returnsSuccessWithList() = runTest {
        val followerId = "repo_follower_2"
        db.collection("users").document(followerId).set(mapOf("id" to followerId)).await()
        
        val result = userRepository.getFollowing(followerId)
        
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isNotNull()
    }

    // 8. Actualizar perfil de usuario
    @Test
    fun updateUserProfile_returnsSuccess() = runTest {
        val user = UserProfileDTO(id = testUserId, username = "Old Repo")
        db.collection("users").document(user.id).set(user).await()

        val result = userRepository.updateUserProfile(
            id = user.id,
            username = "New Repo",
            bio = "New Bio",
            birthDate = null,
            profilePictureUrl = null
        )

        assertThat(result.isSuccess).isTrue()
        
        val updatedDoc = db.collection("users").document(user.id).get().await()
        assertThat(updatedDoc.getString("username")).isEqualTo("New Repo")
    }
}
