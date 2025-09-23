package com.example.unittestsworkshop.data.repository

import com.example.unittestsworkshop.data.api.ApiClient
import com.example.unittestsworkshop.data.exceptions.Error
import com.example.unittestsworkshop.data.requests.LoginRequest
import com.example.unittestsworkshop.data.requests.LogoutRequest
import com.example.unittestsworkshop.data.responses.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(
    private val apiClient: ApiClient
) {
    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(username, password)
                val response = apiClient.execute(request)
                
                if (response.isSuccess) {
                    val data = response.getOrNull()
                    if (data != null) {
                        Result.success(data)
                    } else {
                        Result.failure(Error.ServerError())
                    }
                } else {
                    Result.failure(response.exceptionOrNull() ?: Error.ServerError())
                }
            } catch (e: Exception) {
                Result.failure(Error.NetworkError())
            }
        }
    }

    suspend fun logout(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.execute(LogoutRequest())
                if (response.isSuccess) {
                    Result.success(Unit)
                } else {
                    Result.failure(Error.NetworkError())
                }
            } catch (e: Exception) {
                Result.failure(Error.NetworkError())
            }
        }
    }
}