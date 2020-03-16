package com.example.news.data.api

import com.example.news.domain.models.ResponseFromApi
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {

    @GET("search")
    fun getNewsAsync() : Deferred<Response<ResponseFromApi>>
}