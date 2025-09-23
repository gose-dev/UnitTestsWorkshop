package com.example.unittestsworkshop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittestsworkshop.data.api.FakeApiClient
import com.example.unittestsworkshop.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Data class representing the UI state for the login screen.
 *
 * @property username The entered username
 * @property password The entered password
 * @property isLoading Whether a login request is in progress
 * @property errorMessage Error message to display if login fails
 * @property isLoggedIn Whether the user is successfully logged in
 */
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isLoggedIn: Boolean = false,
    val userId: String = ""
)

/**
 * ViewModel for handling login functionality.
 * Manages UI state and communicates with the LoginRepository to perform login operations.
 */
class LoginViewModel : ViewModel() {
    private val repository = LoginRepository(FakeApiClient())
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Updates the username in the UI state.
     *
     * @param username The new username value
     */
    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    /**
     * Updates the password in the UI state.
     *
     * @param password The new password value
     */
    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    /**
     * Performs the login operation.
     * Validates input, shows loading state, and communicates with the repository to authenticate the user.
     * Updates the UI state based on the result of the login operation.
     */
    fun login() {
        viewModelScope.launch {
            val currentState = _uiState.value
            
            // Validate input
            when {
                currentState.username.isEmpty() -> {
                    _uiState.value = currentState.copy(errorMessage = "Username is required")
                    return@launch
                }
                currentState.password.isEmpty() -> {
                    _uiState.value = currentState.copy(errorMessage = "Password is required")
                    return@launch
                }
            }
            
            // Show loading state
            _uiState.value = currentState.copy(isLoading = true, errorMessage = "")
            
            // Perform login using repository
            val result = repository.login(currentState.username, currentState.password)
            
            if (result.isSuccess) {
                val loginResponse = result.getOrNull()
                _uiState.value = currentState.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    errorMessage = "",
                    userId = loginResponse?.user?.id ?: ""
                )
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Login failed"
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = errorMessage
                )
            }
        }
    }
    
    /**
     * Resets the error message in the UI state.
     */
    fun resetError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }
    
    /**
     * Performs the logout operation.
     * Shows loading state, communicates with the repository to logout the user,
     * and resets the UI state to initial values.
     */
    fun logout() {
        viewModelScope.launch {
            // Show loading state
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Perform logout using repository
            repository.logout()
            _uiState.value = LoginUiState()
        }
    }
}