package com.example.gamercornerapp.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gamercornerapp.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRepositoryIntegrationTest {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var userRemoteDataSource: UserFirestoreDataSourceImpl
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        db = FirebaseFirestore.getInstance()
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (_: Exception) {}

        auth = FirebaseAuth.getInstance()
        userRemoteDataSource = UserFirestoreDataSourceImpl(db, auth)
        userRepository = UserRepository(userRemoteDataSource)
    }

    @Test
    fun getUserById_validId_correctUser() = runTest {
        val batch = db.batch()
        val user = UserProfileDTO(
            id = "user_repo_1",
            username = "Repo User",
            nickName = "@repouser"
        )
        batch.set(db.collection("users").document(user.id), user)
        batch.commit().await()

        val result = userRepository.getUserById("user_repo_1")

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.username).isEqualTo("Repo User")
    }

    @Test
    fun getUserById_invalidId_returnFailure() = runTest {
        val result = userRepository.getUserById("user_999")

        assertThat(result.isFailure).isTrue()
    }
}
