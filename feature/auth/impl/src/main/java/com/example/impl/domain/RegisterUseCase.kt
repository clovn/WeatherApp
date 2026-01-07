package com.example.impl.domain

import com.example.api.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repository.register(email, password)
}