package com.example.impl.domain

import com.example.api.AuthRepository

class IsAuthUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke() = repository.isAuthenticated()
}