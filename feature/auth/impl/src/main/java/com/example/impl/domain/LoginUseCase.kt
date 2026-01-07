package com.example.impl.domain

import com.example.api.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke(email: String, password: String) = repository.login(email, password)
}