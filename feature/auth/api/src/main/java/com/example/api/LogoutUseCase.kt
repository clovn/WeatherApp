package com.example.api

class LogoutUseCase(
    private val repository: AuthRepository
) {
    suspend fun invoke(){
        repository.logout()
    }
}