
package com.example.gamercornerapp.ui.Screens.notes

import com.example.gamercornerapp.data.Note

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val currentUserId: String? = null,
    val selectedNoteId: String? = null,
    val isEditorOpen: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
) {

    val myNote: Note?
        get() = notes.find {
            it.userId == currentUserId
        }

    val selectedNote: Note?
        get() = notes.find {
            it.id == selectedNoteId
        }

    val otherNotes: List<Note>
        get() = notes.filter {
            it.userId != currentUserId
        }
}
