package com.example.unittestsworkshop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittestsworkshop.data.api.FakeApiClient
import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.data.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing user-related UI state and operations.
 * Handles loading, creating, retrieving, and deleting users.
 */
class UsersViewModel(
    private val usersRepository: UsersRepository = UsersRepository(FakeApiClient())
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    /**
     * Sealed class representing the possible UI states for user operations.
     */
    sealed class UiState {
        /** Indicates that an operation is in progress. */
        object Loading : UiState()
        
        /** Indicates that users have been successfully loaded. */
        data class Success(val users: List<User>) : UiState()
        
        /** Indicates that an error occurred during an operation. */
        data class Error(val message: String) : UiState()
        
        /** Indicates that a single user operation was successful. */
        data class UserOperationSuccess(val user: User) : UiState()
        
        /** Indicates that a user deletion was successful. */
        object UserDeleted : UiState()
    }

    /**
     * Loads all users from the repository.
     * Updates the UI state to Loading while the operation is in progress,
     * then to Success with the list of users, or to Error if the operation fails.
     */
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            usersRepository.getUsers()
                .onSuccess { users ->
                    _uiState.value = UiState.Success(users)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Unknown error")
                }
        }
    }
    
    /**
     * Creates a new user.
     * Updates the UI state to Loading while the operation is in progress,
     * then to UserOperationSuccess with the created user, or to Error if the operation fails.
     */
    fun createUser() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            usersRepository.createUser()
                .onSuccess { user ->
                    _uiState.value = UiState.UserOperationSuccess(user)
                    // Optionally reload the user list after creating a user
                    loadUsers()
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Unknown error")
                }
        }
    }
    
    /**
     * Retrieves a user by their ID.
     * Updates the UI state to Loading while the operation is in progress,
     * then to UserOperationSuccess with the retrieved user, or to Error if the operation fails.
     *
     * @param id The ID of the user to retrieve.
     */
    fun getUserById(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            usersRepository.getUserById(id)
                .onSuccess { user ->
                    _uiState.value = UiState.UserOperationSuccess(user)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Unknown error")
                }
        }
    }
    
    /**
     * Deletes a user by their ID.
     * Updates the UI state to Loading while the operation is in progress,
     * then to UserDeleted if successful, or to Error if the operation fails.
     *
     * @param id The ID of the user to delete.
     */
    fun deleteUserById(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            usersRepository.deleteUserById(id)
                .onSuccess {
                    _uiState.value = UiState.UserDeleted
                    // Reload the user list after deleting a user
                    loadUsers()
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Unknown error")
                }
        }
    }
    
    companion object {
        fun factory(): UsersViewModel {
            return UsersViewModel(UsersRepository(FakeApiClient()))
        }
    }
}