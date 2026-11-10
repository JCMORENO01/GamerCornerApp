
package com.example.gamercornerapp.ui.Screens.notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gamercornerapp.data.Note
import com.example.gamercornerapp.data.repository.NoteRepository

@Composable
fun NoteDialog(
    note: Note?,
    isEditing: Boolean,
    isSaving: Boolean,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    onDelete: () -> Unit
) {

    var text by rememberSaveable(note?.id, isEditing) {
        mutableStateOf(note?.text ?: "")
    }

    var showDeleteConfirmation by rememberSaveable {
        mutableStateOf(false)
    }

    if (showDeleteConfirmation) {

        AlertDialog(
            onDismissRequest = {
                showDeleteConfirmation = false
            },
            title = {
                Text("¿Eliminar nota?")
            },
            text = {
                Text("Esta nota desaparecerá de GamerCorner.")
            },
            confirmButton = {
                TextButton(
                    enabled = !isSaving,
                    onClick = {
                        showDeleteConfirmation = false
                        onDelete()
                    }
                ) {
                    Text(
                        text = "Eliminar",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirmation = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )

        return
    }

    AlertDialog(
        onDismissRequest = {
            if (!isSaving) {
                onDismiss()
            }
        },

        title = {
            Text(
                text = when {
                    isEditing && note == null -> "Crear nota"
                    isEditing -> "Editar tu nota"
                    else -> "@${note?.username ?: "Gamer"}"
                },
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        },

        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if (isEditing) {

                    OutlinedTextField(
                        value = text,
                        onValueChange = { newText ->

                            if (
                                newText.length <=
                                NoteRepository.MAX_CHARACTERS
                            ) {
                                text = newText
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("¿Qué estás jugando?")
                        },
                        placeholder = {
                            Text("Comparte algo con otros gamers")
                        },
                        maxLines = 3,
                        enabled = !isSaving
                    )

                    Text(
                        text = "${text.length}/${NoteRepository.MAX_CHARACTERS}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Tu nota estará disponible durante 24 horas.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                } else {

                    Text(
                        text = note?.text.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },

        confirmButton = {

            if (isEditing) {

                Button(
                    onClick = {
                        onSave(text.trim())
                    },
                    enabled = !isSaving && text.isNotBlank()
                ) {
                    Text(
                        if (isSaving) "Guardando..." else "Publicar"
                    )
                }

            } else {

                TextButton(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        },

        dismissButton = {

            Row {

                if (isEditing && note != null) {

                    TextButton(
                        enabled = !isSaving,
                        onClick = {
                            showDeleteConfirmation = true
                        }
                    ) {
                        Text(
                            text = "Eliminar",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                if (isEditing) {

                    TextButton(
                        enabled = !isSaving,
                        onClick = onDismiss
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }
    )
}
