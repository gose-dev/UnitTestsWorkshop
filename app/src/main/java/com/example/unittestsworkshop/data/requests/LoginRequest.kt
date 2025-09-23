package com.example.unittestsworkshop.data.requests

import com.example.unittestsworkshop.data.api.DataProvider
import com.example.unittestsworkshop.data.api.ExecutableRequest
import com.example.unittestsworkshop.data.exceptions.Error
import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.data.responses.LoginResponse

data class LoginRequest(
    private val username: String,
    private val password: String
) : ExecutableRequest<LoginResponse> {

    override fun execute(): Result<LoginResponse> {
        when {
            username.isEmpty() -> {
               return Result.failure(
                   Error.ValidationError("Username is required")
               )
            }
            password.isEmpty() -> {
                return Result.failure(
                    Error.ValidationError("Password is required")
                )
            }
            password.length < 6 -> {
                return Result.failure(
                    Error.ValidationError("Password must be at least 6 characters")
                )
            }
        }

        val user: User? = DataProvider.users.find { it.username == username }

        if (user == null) {
            return Result.failure(Error.InvalidCredentialsError())
        }

        val response = LoginResponse(
            token = "token_${System.currentTimeMillis()}",
            user = user
        )

        return Result.success(response)
    }
}