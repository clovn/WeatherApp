package com.example.impl

import com.example.api.AuthRepository
import com.example.api.AuthResult
import com.example.impl.domain.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {
    private val authRepository = mockk<AuthRepository>()

    // Создаем экземпляр use case
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(authRepository)
    }

    @Test
    fun `should return success when login is successful`() = runBlocking {
        val email = "test@example.com"
        val password = "password123"
        val mockToken = AuthResult.Success("")

        coEvery { authRepository.login(email, password) } returns mockToken

        val result = loginUseCase.invoke(email, password)

        assertEquals(mockToken, result)
    }

    @Test
    fun `should return error when login fails`() = runBlocking {
        // Arrange
        val email = "test@example.com"
        val password = "wrong_password"
        val error = AuthResult.Error(message = "")

        // Мокируем поведение репозитория
        coEvery { authRepository.login(email, password) } returns error

        val result = loginUseCase.invoke(email, password)

        assertEquals(error, result)
    }

    @Test
    fun `should handle unexpected exceptions during login`() = runBlocking {
        val email = "test@example.com"
        val password = "password123"

        coEvery { authRepository.login(email, password) } throws RuntimeException("Unexpected error")

        try {
            loginUseCase.invoke(email, password)
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Unexpected error", e.message)
        }
    }
}