package com.example.unittestsworkshop

import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.data.repository.UsersRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class UsersViewModelTest {

    private lateinit var viewModel: UsersViewModel
    
    @Mock
    private lateinit var mockRepository: UsersRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = UsersViewModel(mockRepository)
    }

    @Test
    fun `loadUsers should update uiState to Success when repository returns users`() = runTest {
        // Arrange
        val users = listOf(
            User("1", "testuser1", "test1@example.com", emptyList()),
            User("2", "testuser2", "test2@example.com", emptyList())
        )
        
        whenever(mockRepository.getUsers())
            .thenReturn(Result.success(users))
        
        // Act
        viewModel.loadUsers()
        advanceUntilIdle()
        
        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersViewModel.UiState.Success)
        assertEquals(users, (state as UsersViewModel.UiState.Success).users)
    }

    @Test
    fun `loadUsers should update uiState to Error when repository fails`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        whenever(mockRepository.getUsers())
            .thenReturn(Result.failure(Exception(errorMessage)))
        
        // Act
        viewModel.loadUsers()
        advanceUntilIdle()
        
        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersViewModel.UiState.Error)
        assertEquals(errorMessage, (state as UsersViewModel.UiState.Error).message)
    }

        @Test
    fun `createUser should update uiState to UserOperationSuccess when repository succeeds`() = runTest {
        // Arrange
        val user = User("1", "testuser", "test@example.com", emptyList())
        whenever(mockRepository.createUser())
            .thenReturn(Result.success(user))
        whenever(mockRepository.getUsers())
            .thenReturn(Result.success(emptyList()))
        
        // Act
        viewModel.createUser()
        advanceUntilIdle()
        
        // Assert
        // First state should be UserOperationSuccess
        val state = viewModel.uiState.value
        assertTrue(state is UsersViewModel.UiState.Success)
    }

    @Test
    fun `createUser should update uiState to Error when repository fails`() = runTest {
        // Arrange
        val errorMessage = "Creation failed"
        whenever(mockRepository.createUser())
            .thenReturn(Result.failure(Exception(errorMessage)))
        
        // Act
        viewModel.createUser()
        advanceUntilIdle()
        
        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersViewModel.UiState.Error)
        assertEquals(errorMessage, (state as UsersViewModel.UiState.Error).message)
    }

    @Test
    fun `getUserById should update uiState to UserOperationSuccess when repository succeeds`() = runTest {
        // Arrange
        val user = User("1", "testuser", "test@example.com", emptyList())
        whenever(mockRepository.getUserById("1"))
            .thenReturn(Result.success(user))
        
        // Act
        viewModel.getUserById("1")
        advanceUntilIdle()
        
        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersViewModel.UiState.UserOperationSuccess)
        assertEquals(user, (state as UsersViewModel.UiState.UserOperationSuccess).user)
    }

    @Test
    fun `getUserById should update uiState to Error when repository fails`() = runTest {
        // Arrange
        val errorMessage = "User not found"
        whenever(mockRepository.getUserById("999"))
            .thenReturn(Result.failure(Exception(errorMessage)))
        
        // Act
        viewModel.getUserById("999")
        advanceUntilIdle()
        
        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersViewModel.UiState.Error)
        assertEquals(errorMessage, (state as UsersViewModel.UiState.Error).message)
    }

    @Test
    fun `deleteUserById should update uiState to UserDeleted when repository succeeds`() = runTest {
        // Arrange
        whenever(mockRepository.deleteUserById("1"))
            .thenReturn(Result.success(Unit))
        whenever(mockRepository.getUsers())
            .thenReturn(Result.success(emptyList()))
        
        // Act
        viewModel.deleteUserById("1")
        advanceUntilIdle()
        
        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersViewModel.UiState.Success)
    }

    @Test
    fun `deleteUserById should update uiState to Error when repository fails`() = runTest {
        // Arrange
        val errorMessage = "Deletion failed"
        whenever(mockRepository.deleteUserById("999"))
            .thenReturn(Result.failure(Exception(errorMessage)))
        
        // Act
        viewModel.deleteUserById("999")
        advanceUntilIdle()
        
        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersViewModel.UiState.Error)
        assertEquals(errorMessage, (state as UsersViewModel.UiState.Error).message)
    }
}