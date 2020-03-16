package com.example.news.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("api-key", "ef3da477-2d87-4065-90d0-cc199ea35016")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}