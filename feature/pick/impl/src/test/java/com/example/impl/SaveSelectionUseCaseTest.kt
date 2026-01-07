package com.example.impl

import com.example.impl.domain.SaveSelectionUseCase
import com.example.local.SharedPrefsManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveSelectionUseCaseTest {
    private val sharedPrefsManager = mockk<SharedPrefsManager>()

    private lateinit var saveSelectionUseCase: SaveSelectionUseCase

    @Before
    fun setUp() {
        saveSelectionUseCase = SaveSelectionUseCase(prefs = sharedPrefsManager)
    }

    @Test
    fun `should call saveSelectedCity with correct city`() = runBlocking {
        val city = "Москва"

        coEvery { sharedPrefsManager.saveSelectedCity(city) } returns Unit

        saveSelectionUseCase.invoke(city)

        coVerify(exactly = 1) { sharedPrefsManager.saveSelectedCity(city) }
    }

    @Test
    fun `should handle unexpected exceptions during saveSelectedCity call`() = runBlocking {
        val city = "Санкт-Петербург"

        coEvery { sharedPrefsManager.saveSelectedCity(city) } throws RuntimeException("Unexpected error")

        try {
            saveSelectionUseCase.invoke(city)
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Unexpected error", e.message)
        }
    }
}