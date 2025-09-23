package com.example.unittestsworkshop.data.repository

import com.example.unittestsworkshop.data.api.ApiClient
import com.example.unittestsworkshop.data.exceptions.Error
import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.data.requests.CreateUserRequest
import com.example.unittestsworkshop.data.requests.DeleteUserByIdRequest
import com.example.unittestsworkshop.data.requests.GetUserByIdRequest
import com.example.unittestsworkshop.data.requests.GetUserListRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class for managing user-related operations.
 * Handles creation, retrieval, and deletion of users through API calls.
 */
class UsersRepository(
    private val apiClient: ApiClient
) {
    /**
     * Creates a new user.
     *
     * @return Result containing the created User on success, or an error on failure.
     */
    suspend fun createUser(): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.execute(CreateUserRequest())
                if (response.isSuccess) {
                    val user = response.getOrNull()?.user
                    if (user != null) {
                        Result.success(user)
                    } else {
                        Result.failure(Error.ServerError())
                    }
                } else {
                    Result.failure(response.exceptionOrNull() ?: Error.ServerError())
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Retrieves a list of all users.
     *
     * @return Result containing a list of Users on success, or an error on failure.
     */
    suspend fun getUsers(): Result<List<User>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.execute(GetUserListRequest())
                if (response.isSuccess) {
                    val users = response.getOrNull()?.users ?: emptyList()
                    Result.success(users)
                } else {
                    Result.failure(response.exceptionOrNull() ?: Error.ServerError())
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return Result containing the User on success, or an error on failure.
     */
    suspend fun getUserById(id: String): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.execute(GetUserByIdRequest(id))
                if (response.isSuccess) {
                    val user = response.getOrNull()?.user
                    if (user != null) {
                        Result.success(user)
                    } else {
                        Result.failure(Error.ServerError())
                    }
                } else {
                    Result.failure(response.exceptionOrNull() ?: Error.ServerError())
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return Result containing Unit on success, or an error on failure.
     */
    suspend fun deleteUserById(id: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.execute(DeleteUserByIdRequest(id))
                if (response.isSuccess) {
                    Result.success(Unit)
                } else {
                    Result.failure(response.exceptionOrNull() ?: Error.ServerError())
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}