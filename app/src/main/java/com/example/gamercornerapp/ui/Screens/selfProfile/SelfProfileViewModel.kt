package com.example.gamercornerapp.ui.Screens.selfProfile

import androidx.lifecycle.ViewModel
import com.example.gamercornerapp.data.local.LocalDataProvider
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import android.net.Uri
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class SelfProfileViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SelfProfileState())
    val uiState: StateFlow<SelfProfileState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                userProfile = LocalDataProvider.userProfile,
                reviews = LocalDataProvider.reviews
            )
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun onLogoutClick() {
        authRepository.signOut()
    }


    fun uploadImageToFirebase(uri: Uri) {
        viewModelScope.launch {
            val result = storageRepository.uploadProfileImage(uri)

            result.onSuccess { downloadUrl ->
                authRepository.updateProfilePicture(downloadUrl)

                //actualizar para renderizar la nueva imagen publica
                _uiState.update { currentState ->
                    currentState.copy(
                        userProfile = currentState.userProfile?.copy(
                            profilePictureUrl = downloadUrl
                        )
                    )
                }
            }.onFailure { error ->
                //TODO- manejo de errores
                android.util.Log.e("FirebaseStorageError", "Error al subir imagen: ${error.message}", error)
            }
        }
    }
}
