
package com.example.gamercornerapp.ui.Screens.notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.data.Note
import com.example.gamercornerapp.ui.Screens.notes.NotesUiState

@Composable
fun NotesSection(
    uiState: NotesUiState,
    onCreateClick: () -> Unit,
    onNoteClick: (String) -> Unit,
    onDismissNote: () -> Unit,
    onCloseEditor: () -> Unit,
    onSaveNote: (String) -> Unit,
    onDeleteNote: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Notas de gamers",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "24 horas",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Nota propia o botón para crearla.
            NoteBubble(
                note = uiState.myNote,
                isOwnNote = true,
                onClick = onCreateClick
            )

            // Notas de los demás usuarios.
            uiState.otherNotes.forEach { note ->

                NoteBubble(
                    note = note,
                    isOwnNote = false,
                    onClick = {
                        onNoteClick(note.id)
                    }
                )
            }
        }

        if (uiState.isLoading) {
            Text(
                text = "Cargando notas...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        uiState.errorMessage?.let { error ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = error,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )

                TextButton(onClick = onDismissError) {
                    Text("Cerrar")
                }
            }
        }
    }

    // Editor para crear o reemplazar la nota propia.
    if (uiState.isEditorOpen) {

        NoteDialog(
            note = uiState.myNote,
            isEditing = true,
            isSaving = uiState.isSaving,
            onDismiss = onCloseEditor,
            onSave = onSaveNote,
            onDelete = onDeleteNote
        )
    }

    // Visualización de una nota seleccionada.
    uiState.selectedNote?.let { note ->

        NoteDialog(
            note = note,
            isEditing = false,
            isSaving = false,
            onDismiss = onDismissNote,
            onSave = {},
            onDelete = {}
        )
    }
}
