package com.example.unittestsworkshop.data.responses

import com.example.unittestsworkshop.data.models.User

data class GetUserListResponse(
    val users: List<User>
)