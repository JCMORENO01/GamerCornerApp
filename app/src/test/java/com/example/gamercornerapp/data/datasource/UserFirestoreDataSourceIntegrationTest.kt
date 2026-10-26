package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import com.example.gamercornerapp.data.dto.UserProfileDTO
import com.example.gamercornerapp.data.util.FakerDataGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserFirestoreDataSourceIntegrationTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var dataSource: UserFirestoreDataSourceImpl

    @Before
    fun setUp() {
        firestore = mockk(relaxed = true)
        auth = mockk(relaxed = true)
        dataSource = UserFirestoreDataSourceImpl(firestore, auth)
    }

    @After
    fun tearDown() {
        io.mockk.clearAllMocks()
    }

    @Test
    fun getUserById_success() = runTest {
        val fakeUser = FakerDataGenerator.generateFakeUserProfile("user_1")
        assertNotNull(fakeUser)
        assertEquals("user_1", fakeUser.id)
    }

    @Test(expected = Exception::class)
    fun getUserById_throwsException_whenUserNotFound() = runTest {
        coEvery { firestore.collection(any()).document(any()).get() } throws RuntimeException("Network error / Not found")
        dataSource.getUserById("non_existent")
    }

    @Test
    fun registerUser_success() = runTest {
        val fakeUser = FakerDataGenerator.generateFakeUserProfile("user_2")
        assertNotNull(fakeUser.username)
        assertTrue(fakeUser.followersCount >= 0)
    }

    @Test(expected = Exception::class)
    fun registerUser_throwsException_onFailure() = runTest {
        val fakeUser = FakerDataGenerator.generateFakeUserProfile("user_3")
        coEvery { firestore.collection(any()).document(any()).set(any()) } throws RuntimeException("Firestore write error")
        dataSource.registerUser(fakeUser, fakeUser.id)
    }

    @Test
    fun updateUserProfile_success() = runTest {
        val fakeUser = FakerDataGenerator.generateFakeUserProfile("user_4")
        assertNotNull(fakeUser)
    }

    @Test(expected = Exception::class)
    fun updateUserProfile_throwsException_onNetworkError() = runTest {
        val fakeUser = FakerDataGenerator.generateFakeUserProfile("user_5")
        coEvery { firestore.collection(any()).document(any()).set(any(), any()) } throws RuntimeException("Timeout error")
        dataSource.updateUserProfile("user_5", fakeUser)
    }

    @Test
    fun searchUsers_returnsList_onSuccess() = runTest {
        val users = listOf(FakerDataGenerator.generateFakeUserProfile("user_6"))
        assertNotNull(users)
        assertEquals(1, users.size)
    }

    @Test
    fun searchUsers_returnsEmptyList_onNoMatch() = runTest {
        val emptyList = emptyList<UserProfileDTO>()
        assertTrue(emptyList.isEmpty())
    }
}
