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
    fun tearDown() {
        // Nada que limpiar manualmente en este caso
    }

    // 1. Obtener usuario existente
    @Test
    fun getUserById_validId_correctUser() = runTest {
        val user = UserProfileDTO(id = "user_test_1", username = "Juan")
        db.collection("users").document(user.id).set(user).await()

        val result = dataSource.getUserById(user.id)
        assertThat(result).isNotNull()
        assertThat(result.username).isEqualTo("Juan")
    }

    // 2. Obtener usuario no existente lanza excepcion
    @Test(expected = Exception::class)
    fun getUserById_invalidId_throwsException() = runTest {
        dataSource.getUserById("invalid_id")
    }

    // 3. Registrar nuevo usuario
    @Test
    fun registerUser_createsNewDocument() = runTest {
        val user = UserProfileDTO(id = "user_test_3", username = "Carlos")
        dataSource.registerUser(user, user.id)

        val doc = db.collection("users").document(user.id).get().await()
        assertThat(doc.exists()).isTrue()
        assertThat(doc.getString("username")).isEqualTo("Carlos")
    }

    // 4. Actualizar usuario existente
    @Test
    fun updateUserProfile_updatesFieldsCorrectly() = runTest {
        val user = UserProfileDTO(id = testUserId, username = "Old Name")
        db.collection("users").document(testUserId).set(user).await()

        val updatedUser = user.copy(username = "New Name", bio = "New Bio")
        
        // Ahora usamos directamente dataSource porque testUserId == auth.currentUser.uid
        dataSource.updateUserProfile(testUserId, updatedUser)

        val doc = db.collection("users").document(testUserId).get().await()
        assertThat(doc.getString("username")).isEqualTo("New Name")
        assertThat(doc.getString("bio")).isEqualTo("New Bio")
    }

    // 5. Buscar usuarios (Query)
    @Test
    fun searchUsers_returnsMatchingUsers() = runTest {
        val user1 = UserProfileDTO(id = "search_1", username = "GamerOne", nickName = "gamerone")
        val user2 = UserProfileDTO(id = "search_2", username = "GamerTwo", nickName = "gamertwo")
        val user3 = UserProfileDTO(id = "search_3", username = "Player", nickName = "player")
        
        db.collection("users").document(user1.id).set(user1).await()
        db.collection("users").document(user2.id).set(user2).await()
        db.collection("users").document(user3.id).set(user3).await()

        val results = dataSource.searchUsers("gamer")
        assertThat(results.size).isAtLeast(2)
    }

    // 6. Seguir a un usuario
    @Test
    fun toggleFollowUser_addsToFollowing() = runTest {
        val currentUserId = testUserId
        val targetUserId = "target_1"
        
        // Setup initial documents so transaction doesn't fail
        db.collection("users").document(currentUserId).set(mapOf("id" to currentUserId)).await()
        db.collection("users").document(targetUserId).set(mapOf("id" to targetUserId)).await()

        // Llamamos al DataSource real! Ya no tirará "Usuario no autenticado" porque usamos la sesión de testUserId
        dataSource.toggleFollowUser(currentUserId, targetUserId)

        val isFollowing = dataSource.isFollowing(currentUserId, targetUserId)
        assertThat(isFollowing).isTrue()
    }

    // 7. Dejar de seguir a un usuario
    @Test
    fun toggleFollowUser_twice_removesFromFollowing() = runTest {
        val currentUserId = testUserId
        val targetUserId = "target_2"
        
        db.collection("users").document(currentUserId).set(mapOf("id" to currentUserId)).await()
        db.collection("users").document(targetUserId).set(mapOf("id" to targetUserId)).await()

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
        
        db.collection("users").document(currentUserId).set(mapOf("id" to currentUserId)).await()
        db.collection("users").document(followerId).set(mapOf("id" to followerId)).await()

        // Follower sigue a target
        dataSource.toggleFollowUser(followerId, currentUserId)

        val followers = dataSource.getFollowers(currentUserId)
        assertThat(followers).contains(followerId)
    }
}
