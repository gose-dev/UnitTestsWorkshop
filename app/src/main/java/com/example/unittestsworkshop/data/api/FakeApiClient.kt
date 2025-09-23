package com.example.unittestsworkshop.data.api

import kotlinx.coroutines.delay

class FakeApiClient : ApiClient {

    override suspend fun <Response> execute(request: ExecutableRequest<Response>): Result<Response> {
        delay(800)
        return request.execute()
    }
}