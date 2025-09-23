package com.example.unittestsworkshop.data.requests

import com.example.unittestsworkshop.data.api.DataProvider
import com.example.unittestsworkshop.data.api.ExecutableRequest
import com.example.unittestsworkshop.data.exceptions.Error
import com.example.unittestsworkshop.data.models.User

class DeleteUserByIdRequest(private val id: String) : ExecutableRequest<Unit> {

    override fun execute(): Result<Unit> {
        val user: User? = DataProvider.users.find { it.id == id }
        if (user == null) {
            return Result.failure(Error.NoSuchUserError())
        }
        DataProvider.users.remove(user)
        return Result.success(Unit)
    }
}