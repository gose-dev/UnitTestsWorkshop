package com.example.unittestsworkshop.data.repository

import com.example.unittestsworkshop.data.api.ApiClient
import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.data.requests.CreateUserRequest
import com.example.unittestsworkshop.data.requests.DeleteUserByIdRequest
import com.example.unittestsworkshop.data.requests.GetUserByIdRequest
import com.example.unittestsworkshop.data.requests.GetUserListRequest
import com.example.unittestsworkshop.data.responses.CreateUserResponse
import com.example.unittestsworkshop.data.responses.GetUserByIdResponse
import com.example.unittestsworkshop.data.responses.GetUserListResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class UsersRepositoryTest {

    private lateinit var repository: UsersRepository
    
    @Mock
    private lateinit var mockApiClient: ApiClient

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = UsersRepository(mockApiClient)
    }

    @Test
    fun `createUser returns success when api call succeeds`() = runTest {
        // Arrange
        val user = User("1", "testuser", "testuser@example.com", emptyList())
        val response = CreateUserResponse(user)

        whenever(mockApiClient.execute(any<CreateUserRequest>()))
            .thenReturn(Result.success(response))
        
        // Act
        val result = repository.createUser()
        
        // Assert
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `createUser returns failure when api call fails`() = runTest {
        // Arrange
        val exception = Exception("API error")
        whenever(mockApiClient.execute(any<CreateUserRequest>()))
            .thenReturn(Result.failure(exception))
        
        // Act
        val result = repository.createUser()
        
        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getUsers returns success when api call succeeds`() = runTest {
        // Arrange
        val users = listOf(
            User("1", "testuser1", "testuser1@example.com", emptyList()),
            User("2", "testuser2", "testuser2@example.com", emptyList())
        )
        val response = GetUserListResponse(users)

        whenever(mockApiClient.execute(any<GetUserListRequest>()))
            .thenReturn(Result.success(response))
        
        // Act
        val result = repository.getUsers()
        
        // Assert
        assertTrue(result.isSuccess)
        assertEquals(users, result.getOrNull())
    }

    @Test
    fun `getUsers returns failure when api call fails`() = runTest {
        // Arrange
        val exception = Exception("API error")
        whenever(mockApiClient.execute(any<GetUserListRequest>()))
            .thenReturn(Result.failure(exception))
        
        // Act
        val result = repository.getUsers()
        
        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getUserById returns success when api call succeeds`() = runTest {
        // Arrange
        val user = User("1", "testuser", "testuser@example.com", emptyList())
        val response = GetUserByIdResponse(user)

        whenever(mockApiClient.execute(any<GetUserByIdRequest>()))
            .thenReturn(Result.success(response))
        
        // Act
        val result = repository.getUserById("1")
        
        // Assert
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `getUserById returns failure when api call fails`() = runTest {
        // Arrange
        val exception = Exception("API error")
        whenever(mockApiClient.execute(any<GetUserByIdRequest>()))
            .thenReturn(Result.failure(exception))
        
        // Act
        val result = repository.getUserById("1")
        
        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `deleteUserById returns success when api call succeeds`() = runTest {
        // Arrange
        whenever(mockApiClient.execute(any<DeleteUserByIdRequest>()))
            .thenReturn(Result.success(Unit))
        
        // Act
        val result = repository.deleteUserById("1")
        
        // Assert
        assertTrue(result.isSuccess)
    }

    @Test
    fun `deleteUserById returns failure when api call fails`() = runTest {
        // Arrange
        val exception = Exception("API error")
        whenever(mockApiClient.execute(any<DeleteUserByIdRequest>()))
            .thenReturn(Result.failure(exception))
        
        // Act
        val result = repository.deleteUserById("1")
        
        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}