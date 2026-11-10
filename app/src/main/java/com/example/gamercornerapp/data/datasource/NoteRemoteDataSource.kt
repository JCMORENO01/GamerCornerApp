
package com.example.gamercornerapp.data.datasource

import com.example.gamercornerapp.data.dto.NoteDTO
import kotlinx.coroutines.flow.Flow

interface NoteRemoteDataSource {

    fun listenActiveNotes(
        sinceMillis: Long
    ): Flow<List<NoteDTO>>

    suspend fun createOrUpdateNote(
        note: NoteDTO
    )

    suspend fun deleteNote(
        userId: String
    )
}
