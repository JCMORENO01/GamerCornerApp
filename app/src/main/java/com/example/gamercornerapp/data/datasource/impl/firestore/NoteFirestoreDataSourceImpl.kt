
package com.example.gamercornerapp.data.datasource.impl.firestore

import com.example.gamercornerapp.data.datasource.NoteRemoteDataSource
import com.example.gamercornerapp.data.dto.NoteDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoteFirestoreDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : NoteRemoteDataSource {

    private val notesCollection
        get() = db.collection("notes")

    override fun listenActiveNotes(
        sinceMillis: Long
    ): Flow<List<NoteDTO>> = callbackFlow {

        val listener = notesCollection
            .whereGreaterThan(
                "expiresAtMillis",
                sinceMillis
            )
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val notes = snapshot
                    ?.documents
                    ?.mapNotNull { document ->

                        document.toObject(
                            NoteDTO::class.java
                        )?.copy(id = document.id)

                    }
                    ?: emptyList()

                trySend(notes)
            }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun createOrUpdateNote(
        note: NoteDTO
    ) {

        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException(
                "Usuario no autenticado"
            )

        require(note.userId == currentUserId) {
            "No puedes publicar notas de otro usuario"
        }

        require(note.text.isNotBlank()) {
            "La nota no puede estar vacía"
        }

        require(note.text.length <= 60) {
            "La nota no puede superar 60 caracteres"
        }

        require(note.expiresAtMillis > note.createdAtMillis) {
            "La fecha de expiración no es válida"
        }

        notesCollection
            .document(currentUserId)
            .set(
                note.copy(
                    id = currentUserId
                )
            )
            .await()
    }

    override suspend fun deleteNote(
        userId: String
    ) {

        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException(
                "Usuario no autenticado"
            )

        require(userId == currentUserId) {
            "No puedes eliminar notas de otro usuario"
        }

        notesCollection
            .document(currentUserId)
            .delete()
            .await()
    }
}
