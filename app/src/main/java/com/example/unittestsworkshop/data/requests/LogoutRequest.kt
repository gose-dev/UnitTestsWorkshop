package com.example.unittestsworkshop.data.requests

import com.example.unittestsworkshop.data.api.ExecutableRequest

class LogoutRequest() : ExecutableRequest<Unit> {

    override fun execute(): Result<Unit> {
        return Result.success(Unit)
    }
}