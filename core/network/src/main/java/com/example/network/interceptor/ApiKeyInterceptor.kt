package com.example.network.interceptor

import com.example.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer " + Constants.API_KEY)
            .build()

        return chain.proceed(newRequest)
    }
}