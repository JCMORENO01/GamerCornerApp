
package com.example.gamercornerapp.ui.Screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        NotesUiState(
            currentUserId = authRepository.currentUser?.uid
        )
    )

    val uiState: StateFlow<NotesUiState> =
        _uiState.asStateFlow()

    private var notesJob: Job? = null

    companion object {
        private const val REFRESH_INTERVAL = 60_000L
    }

    init {
        loadNotes()

        viewModelScope.launch {
            while (isActive) {
                delay(REFRESH_INTERVAL)
                loadNotes()
            }
        }
    }

    fun loadNotes() {

        notesJob?.cancel()

        notesJob = viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = it.notes.isEmpty(),
                    currentUserId =
                        authRepository.currentUser?.uid,
                    errorMessage = null
                )
            }

            val now = System.currentTimeMillis()

            repository.listenActiveNotes(now)
                .catch { error ->

                    if (error is CancellationException) {
                        throw error
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage =
                                error.localizedMessage
                                    ?: "No se pudieron cargar las notas"
                        )
                    }
                }
                .collect { notes ->

                    _uiState.update { currentState ->

                        val selectedId =
                            currentState.selectedNoteId

                        val validSelection = notes.any {
                            it.id == selectedId
                        }

                        currentState.copy(
                            notes = notes,
                            selectedNoteId =
                                if (validSelection) {
                                    selectedId
                                } else {
                                    null
                                },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun openEditor() {
        _uiState.update {
            it.copy(
                isEditorOpen = true,
                selectedNoteId = null,
                errorMessage = null
            )
        }
    }

    fun closeEditor() {
        _uiState.update {
            it.copy(
                isEditorOpen = false
            )
        }
    }

    fun selectNote(noteId: String) {

        _uiState.update { currentState ->

            val exists = currentState.notes.any {
                it.id == noteId
            }

            if (exists) {
                currentState.copy(
                    selectedNoteId = noteId,
                    isEditorOpen = false
                )
            } else {
                currentState
            }
        }
    }

    fun clearSelection() {
        _uiState.update {
            it.copy(selectedNoteId = null)
        }
    }

    fun saveNote(text: String) {

        if (_uiState.value.isSaving) return

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isSaving = true,
                    errorMessage = null
                )
            }

            val result = repository.createOrUpdateNote(text)

            if (result.isSuccess) {

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isEditorOpen = false
                    )
                }

            } else {

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage =
                            result.exceptionOrNull()
                                ?.localizedMessage
                                ?: "No se pudo guardar la nota"
                    )
                }
            }
        }
    }

    fun deleteNote() {

        if (_uiState.value.isSaving) return

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isSaving = true,
                    errorMessage = null
                )
            }

            val result = repository.deleteNote()

            if (result.isSuccess) {

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isEditorOpen = false,
                        selectedNoteId = null
                    )
                }

            } else {

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage =
                            result.exceptionOrNull()
                                ?.localizedMessage
                                ?: "No se pudo eliminar la nota"
                    )
                }
            }
        }
    }

    fun dismissError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}
