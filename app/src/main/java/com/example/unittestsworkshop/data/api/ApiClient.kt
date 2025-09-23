package com.example.unittestsworkshop.data.api

interface ApiClient {
    suspend fun <Response> execute(request: ExecutableRequest<Response>): Result<Response>
}