package com.example.unittestsworkshop.data.models

data class User(
    val id: String,
    val username: String,
    val email: String,
    val friends: List<String> = emptyList()
)