package com.example.gamercornerapp.data.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gamercornerapp.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebaseUserDataSourceTest {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var dataSource: UserFirestoreDataSourceImpl

    @Before
    fun setUp() {
        db = FirebaseFirestore.getInstance()
        try {
            db.useEmulator("10.0.2.2", 8080)
        } catch (_: Exception) {}

        auth = FirebaseAuth.getInstance()
        dataSource = UserFirestoreDataSourceImpl(db, auth)
    }

    @After
    fun tearDown() {
        // Clean up if needed
    }

    @Test
    fun getUserById_validId_correctUser() = runTest {
        val batch = db.batch()
        for (i in 1..10) {
            val user = UserProfileDTO(
                id = "user_$i",
                username = "User $i",
                nickName = "@user$i"
            )
            batch.set(db.collection("users").document(user.id), user)
        }
        batch.commit().await()

        val id = "user_9"
        val expectedUsername = "User 9"

        val result = dataSource.getUserById(id)

        assertThat(result).isNotNull()
        assertThat(result.username).isEqualTo(expectedUsername)
        assertThat(result.id).isEqualTo(id)
    }
}
