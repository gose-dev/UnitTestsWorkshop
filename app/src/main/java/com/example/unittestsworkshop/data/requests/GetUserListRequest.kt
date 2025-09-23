package com.example.unittestsworkshop.data.requests

import com.example.unittestsworkshop.data.api.DataProvider
import com.example.unittestsworkshop.data.api.ExecutableRequest
import com.example.unittestsworkshop.data.responses.GetUserListResponse

class GetUserListRequest() : ExecutableRequest<GetUserListResponse> {

    override fun execute(): Result<GetUserListResponse> {
        return Result.success(GetUserListResponse(DataProvider.users))
    }
}