package com.example.news.data.api

import com.example.news.domain.models.ResponseFromApi
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

//https://content.guardianapis.com/search?page=3&api-key=ef3da477-2d87-4065-90d0-cc199ea35016
interface NewsApi {

    @GET("search")
    fun getNewsAsync(@Query("page") page: Int): Deferred<Response<ResponseFromApi>>

}