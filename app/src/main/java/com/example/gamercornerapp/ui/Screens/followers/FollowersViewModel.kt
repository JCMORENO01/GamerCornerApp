
package com.example.gamercornerapp.ui.Screens.followers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.R
import com.example.gamercornerapp.data.FollowerItem
import com.example.gamercornerapp.data.UserProfile
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FollowersState())
    val uiState: StateFlow<FollowersState> = _uiState.asStateFlow()

    private var profileId: String? = null
    private var requestVersion = 0
    private val pendingFollows = mutableSetOf<String>()

    // Función para cargar seguidores y seguidos
    fun loadFollowers(userId: String, initialTabIndex: Int = 0) {
        if (userId.isBlank()) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Usuario no válido"
                )
            }
            return
        }

        profileId = userId
        val version = ++requestVersion

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    selectedTabIndex = initialTabIndex,
                    errorMessage = null
                )
            }

            val followersResult = userRepository.getFollowers(userId)
            val followingResult = userRepository.getFollowing(userId)

            if (version != requestVersion) return@launch

            if (followersResult.isFailure || followingResult.isFailure) {
                val error = followersResult.exceptionOrNull()
                    ?: followingResult.exceptionOrNull()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error?.localizedMessage
                            ?: "No se pudieron cargar los usuarios"
                    )
                }
                return@launch
            }

            val currentUserId = authRepository.currentUser?.uid

            try {
                val followingIds = if (currentUserId != null) {
                    userRepository.getFollowing(currentUserId)
                        .getOrThrow()
                        .map { it.id }
                        .toSet()
                } else {
                    emptySet()
                }

                val followers = followersResult.getOrThrow()
                    .map { profile ->
                        profile.toFollowerItem(
                            isFollowing = profile.id in followingIds
                        )
                    }

                val following = followingResult.getOrThrow()
                    .map { profile ->
                        profile.toFollowerItem(
                            isFollowing = profile.id in followingIds
                        )
                    }

                if (version != requestVersion) return@launch

                _uiState.update {
                    it.copy(
                        followers = followers,
                        following = following,
                        isLoading = false,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                if (version != requestVersion) return@launch

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage
                    )
                }
            }
        }
    }

    // Función para convertir un perfil a FollowerItem
    private fun UserProfile.toFollowerItem(
        isFollowing: Boolean
    ): FollowerItem {
        return FollowerItem(
            id = id,
            username = username,
            handle = "@$nickName",
            avatarImage = R.drawable.messi1,
            avatarUrl = profilePictureUrl,
            isFollowing = isFollowing
        )
    }

    // Función para cambiar entre seguidores y seguidos
    fun onTabSelected(index: Int) {
        if (index !in 0..1) return

        _uiState.update {
            it.copy(selectedTabIndex = index)
        }
    }

    // Función para seguir o dejar de seguir
    fun toggleFollowUser(targetUserId: String) {
        val currentUserId = authRepository.currentUser?.uid

        if (currentUserId == null) {
            _uiState.update {
                it.copy(errorMessage = "Debes iniciar sesión")
            }
            return
        }

        if (currentUserId == targetUserId) return
        if (!pendingFollows.add(targetUserId)) return

        viewModelScope.launch {
            try {
                val result = userRepository.toggleFollowUser(
                    currentUserId,
                    targetUserId
                )

                if (result.isSuccess) {
                    profileId?.let { loadFollowers(it) }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exceptionOrNull()
                                ?.localizedMessage
                                ?: "No se pudo actualizar el seguimiento"
                        )
                    }
                }
            } finally {
                pendingFollows.remove(targetUserId)
            }
        }
    }
}
