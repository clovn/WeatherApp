package com.example.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            Result.success(response)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}