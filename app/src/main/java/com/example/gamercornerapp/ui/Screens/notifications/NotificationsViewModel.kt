package com.example.gamercornerapp.ui.Screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamercornerapp.data.repository.AuthRepository
import com.example.gamercornerapp.data.repository.NotificationRepository
import com.example.gamercornerapp.ui.Screens.notifications.enums.NotificationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())

    val uiState: StateFlow<NotificationsState> =
        _uiState.asStateFlow()

    private var notificationsJob: Job? = null

    init {
        loadNotifications()
    }

    fun onFilterSelected(filter: NotificationFilter) {
        _uiState.update { state ->
            val filtered = when (filter) {
                NotificationFilter.ALL -> state.allNotifications
                NotificationFilter.LIKES -> state.allNotifications.filter { it.type == NotificationType.LIKE }
                NotificationFilter.FOLLOWERS -> state.allNotifications.filter { it.type == NotificationType.FOLLOW }
                NotificationFilter.COMMENTS -> state.allNotifications.filter { it.type == NotificationType.COMMENT }
            }
            state.copy(
                selectedFilter = filter,
                notifications = filtered,
                isDropdownExpanded = false
            )
        }
    }

    fun onToggleDropdown(expanded: Boolean) {
        _uiState.update { it.copy(isDropdownExpanded = expanded) }
    }

    private fun loadNotifications() {
        val userId = authRepository.currentUser?.uid

        if (userId == null) {
            _uiState.update {
                it.copy(
                    notifications = emptyList(),
                    isLoading = false,
                    errorMessage = "Usuario no autenticado"
                )
            }
            return
        }

        notificationsJob?.cancel()

        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        notificationsJob = viewModelScope.launch {
            try {
                notificationRepository
                    .listenNotifications(userId)
                    .collect { notifications ->
                        _uiState.update { state ->
                            val currentFilter = state.selectedFilter
                            val filtered = when (currentFilter) {
                                NotificationFilter.ALL -> notifications
                                NotificationFilter.LIKES -> notifications.filter { it.type == NotificationType.LIKE }
                                NotificationFilter.FOLLOWERS -> notifications.filter { it.type == NotificationType.FOLLOW }
                                NotificationFilter.COMMENTS -> notifications.filter { it.type == NotificationType.COMMENT }
                            }
                            state.copy(
                                allNotifications = notifications,
                                notifications = filtered,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage
                            ?: "Error al cargar las notificaciones"
                    )
                }
            }
        }
    }

    override fun onCleared() {
        notificationsJob?.cancel()
        super.onCleared()
    }
}
