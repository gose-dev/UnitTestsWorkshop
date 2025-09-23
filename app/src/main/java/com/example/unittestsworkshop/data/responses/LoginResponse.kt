package com.example.unittestsworkshop.data.responses

import com.example.unittestsworkshop.data.models.User

data class LoginResponse(
    val token: String,
    val user: User
)