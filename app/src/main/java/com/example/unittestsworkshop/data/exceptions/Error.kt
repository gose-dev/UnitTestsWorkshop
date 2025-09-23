package com.example.unittestsworkshop.data.exceptions

sealed class Error(message: String) : Exception(message) {
    class InvalidCredentialsError : Error("Invalid username or password")
    class NetworkError : Error("Network error occurred")
    class ServerError : Error("Server error occurred")
    class NoSuchUserError : Error("No such user")
    class NoSuchFriendError : Error("No such friend")
    class ValidationError(message: String) : Error(message)
}