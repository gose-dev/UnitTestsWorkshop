package com.example.unittestsworkshop.data.repository

import com.example.unittestsworkshop.data.api.ApiClient
import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.data.requests.LoginRequest
import com.example.unittestsworkshop.data.requests.LogoutRequest
import com.example.unittestsworkshop.data.responses.LoginResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class LoginRepositoryTest {

    private lateinit var repository: LoginRepository
    
    @Mock
    private lateinit var mockApiClient: ApiClient

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = LoginRepository(mockApiClient)
    }

    @Test
    fun `login with valid credentials returns success`() = runTest {
        // Arrange
        val username = "testuser"
        val password = "password123"
        val user = User("1", username, "${username}@example.com", emptyList())
        val response = LoginResponse("token123", user)

        whenever(mockApiClient.execute(any<LoginRequest>()))
            .thenReturn(Result.success(response))
        
        // Act
        val result = repository.login(username, password)
        
        // Assert
        assertTrue(result.isSuccess)
        val loginResponse = result.getOrNull()
        assertEquals("token123", loginResponse?.token)
        assertEquals(user, loginResponse?.user)
    }

    @Test
    fun `login with empty username returns validation error`() = runTest {
        // Arrange
        val username = ""
        val password = "password123"
        
        whenever(mockApiClient.execute(any<LoginRequest>())).thenReturn(Result.failure(Exception("Username is required")))
        
        // Act
        val result = repository.login(username, password)
        
        // Assert
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Username is required") == true)
    }

    @Test
    fun `login with empty password returns validation error`() = runTest {
        // Arrange
        val username = "testuser"
        val password = ""
        
        whenever(mockApiClient.execute(any<LoginRequest>())).thenReturn(Result.failure(Exception("Password is required")))
        
        // Act
        val result = repository.login(username, password)
        
        // Assert
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Password is required") == true)
    }

    @Test
    fun `login with short password returns validation error`() = runTest {
        // Arrange
        val username = "testuser"
        val password = "123"
        
        whenever(mockApiClient.execute(any<LoginRequest>())).thenReturn(Result.failure(Exception("Password must be at least 6 characters")))
        
        // Act
        val result = repository.login(username, password)
        
        // Assert
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Password must be at least 6 characters") == true)
    }

    @Test
    fun `logout returns success`() = runTest {
        // Arrange
        whenever(mockApiClient.execute(any<LogoutRequest>())).thenReturn(Result.success(Unit))
        
        // Act
        val result = repository.logout()
        
        // Assert
        assertTrue(result.isSuccess)
    }
}