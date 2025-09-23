package com.example.unittestsworkshop.data.api

interface ExecutableRequest<Response> {
    fun execute(): Result<Response>
}