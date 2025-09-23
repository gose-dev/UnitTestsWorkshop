package com.example.unittestsworkshop.data.requests

import com.example.unittestsworkshop.data.api.DataProvider
import com.example.unittestsworkshop.data.api.ExecutableRequest
import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.data.responses.CreateUserResponse

class CreateUserRequest() : ExecutableRequest<CreateUserResponse> {

    override fun execute(): Result<CreateUserResponse> {
        val id = DataProvider.users.size.toString()
        val newUser = User(
            id = id,
            username = "user_$id",
            email = "user_$id@example.com",
            friends = emptyList()
        )
        DataProvider.users.add(newUser)
        return Result.success(CreateUserResponse(newUser))
    }
}