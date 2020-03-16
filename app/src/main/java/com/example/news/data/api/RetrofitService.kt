package com.example.news.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//https://content.guardianapis.com/search?api-key=ef3da477-2d87-4065-90d0-cc199ea35016
private const val BASE_URL = "https://content.guardianapis.com/"
private const val API_KEY = "ef3da477-2d87-4065-90d0-cc199ea35016"

object RetrofitService {

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    private val author = Interceptor { chain ->
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().header("api-key", API_KEY).build()
        chain.proceed(request)
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(author)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}



