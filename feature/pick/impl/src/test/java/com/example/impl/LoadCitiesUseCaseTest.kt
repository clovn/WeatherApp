package com.example.impl

import com.example.impl.domain.LoadCitiesUseCase
import com.example.local.SharedPrefsManager
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoadCitiesUseCaseTest {
    private val sharedPrefsManager = mockk<SharedPrefsManager>()

    private lateinit var loadCitiesUseCase: LoadCitiesUseCase

    @Before
    fun setUp() {
        loadCitiesUseCase = LoadCitiesUseCase(sharedPrefsManager)
    }

    @Test
    fun `should return list of cities when getCitiesList is not empty`() = runBlocking {
        val mockCitiesList = listOf("Москва", "Санкт-Петербург", "Новосибирск")

        coEvery { sharedPrefsManager.getCitiesList() } returns mockCitiesList

        val result = loadCitiesUseCase.invoke()

        assertEquals(mockCitiesList, result)
    }

    @Test
    fun `should return empty list when getCitiesList is empty`() = runBlocking {
        coEvery { sharedPrefsManager.getCitiesList() } returns emptyList()

        val result = loadCitiesUseCase.invoke()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return empty list when getCitiesList returns empty list`() = runBlocking {
        coEvery { sharedPrefsManager.getCitiesList() } returns emptyList()

        val result = loadCitiesUseCase.invoke()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should handle unexpected exceptions during getCitiesList call`() = runBlocking {
        coEvery { sharedPrefsManager.getCitiesList() } throws RuntimeException("Unexpected error")

        try {
            loadCitiesUseCase.invoke()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Unexpected error", e.message)
        }
    }
}