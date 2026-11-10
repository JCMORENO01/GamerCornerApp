
package com.example.gamercornerapp.data.repository

import com.example.gamercornerapp.data.Note
import com.example.gamercornerapp.data.datasource.NoteRemoteDataSource
import com.example.gamercornerapp.data.dto.NoteDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val remoteDataSource: NoteRemoteDataSource,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    companion object {
        const val MAX_CHARACTERS = 60
        const val NOTE_DURATION = 24L * 60 * 60 * 1000
    }

    fun listenActiveNotes(
        currentTimeMillis: Long
    ): Flow<List<Note>> {

        return remoteDataSource
            .listenActiveNotes(currentTimeMillis)
            .map { notes ->

                val now = System.currentTimeMillis()

                notes.filter {
                    it.expiresAtMillis > now &&
                            it.createdAtMillis <= now &&
                            it.userId.isNotBlank()
                }.map { it.toNote() }
                    .sortedByDescending {
                        it.createdAtMillis
                    }
            }
    }

    suspend fun createOrUpdateNote(
        text: String
    ): Result<Unit> {

        return try {

            val userId = authRepository.currentUser?.uid
                ?: throw IllegalStateException(
                    "Debes iniciar sesión"
                )

            val cleanText = text.trim()

            require(cleanText.isNotEmpty()) {
                "La nota no puede estar vacía"
            }

            require(cleanText.length <= MAX_CHARACTERS) {
                "La nota no puede superar 60 caracteres"
            }

            val user = userRepository
                .getUserById(userId)
                .getOrThrow()

            val now = System.currentTimeMillis()

            val note = NoteDTO(
                id = userId,
                userId = userId,
                username = user.username,
                profilePictureUrl =
                    user.profilePictureUrl,
                text = cleanText,
                createdAtMillis = now,
                expiresAtMillis = now + NOTE_DURATION
            )

            remoteDataSource.createOrUpdateNote(note)

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteNote(): Result<Unit> {

        return try {

            val userId = authRepository.currentUser?.uid
                ?: throw IllegalStateException(
                    "Debes iniciar sesión"
                )

            remoteDataSource.deleteNote(userId)

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun NoteDTO.toNote(): Note {
        return Note(
            id = id,
            userId = userId,
            username = username,
            profilePictureUrl = profilePictureUrl,
            text = text,
            createdAtMillis = createdAtMillis,
            expiresAtMillis = expiresAtMillis
        )
    }
}
