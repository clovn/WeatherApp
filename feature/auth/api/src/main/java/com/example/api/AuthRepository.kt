package com.example.api

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult

    suspend fun register(email: String, password: String): AuthResult

    suspend fun isAuthenticated(): Boolean

    suspend fun logout()
}

sealed class AuthResult{
    data class Success(val userId: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}