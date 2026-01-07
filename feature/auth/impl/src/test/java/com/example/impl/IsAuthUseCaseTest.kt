package com.example.impl

import com.example.api.AuthRepository
import com.example.impl.domain.IsAuthUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class IsAuthUseCaseTest {
    private val authRepository = mockk<AuthRepository>()

    private lateinit var isAuthUseCase: IsAuthUseCase

    @Before
    fun setUp() {
        isAuthUseCase = IsAuthUseCase(authRepository)
    }

    @Test
    fun `should return true when user is authenticated`() = runBlocking {
        coEvery { authRepository.isAuthenticated() } returns true

        val result = isAuthUseCase.invoke()

        assertTrue(result)
    }

    @Test
    fun `should return false when user is not authenticated`() = runBlocking {
        coEvery { authRepository.isAuthenticated() } returns false

        val result = isAuthUseCase.invoke()

        assertFalse(result)
    }

    @Test
    fun `should handle unexpected exceptions during authentication check`() = runBlocking {
        coEvery { authRepository.isAuthenticated() } throws RuntimeException("Unexpected error")

        try {
            isAuthUseCase.invoke()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Unexpected error", e.message)
        }
    }
}