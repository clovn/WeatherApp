package com.example.network.interceptor

import com.example.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .url(
                request.url.newBuilder()
                    .addQueryParameter("language", Constants.LANGUAGE)
                    .build()
            )
            .build()

        return chain.proceed(newRequest)
    }
}