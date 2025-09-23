package com.example.unittestsworkshop.data.requests

import com.example.unittestsworkshop.data.api.DataProvider
import com.example.unittestsworkshop.data.api.ExecutableRequest
import com.example.unittestsworkshop.data.exceptions.Error
import com.example.unittestsworkshop.data.models.User
import com.example.unittestsworkshop.data.responses.GetUserByIdResponse

class GetUserByIdRequest(private val id: String) : ExecutableRequest<GetUserByIdResponse> {

    override fun execute(): Result<GetUserByIdResponse> {
        val user: User? = DataProvider.users.find { it.id == id }
        if (user == null) {
            return Result.failure(Error.NoSuchUserError())
        }
        return Result.success(GetUserByIdResponse(user))
    }
}