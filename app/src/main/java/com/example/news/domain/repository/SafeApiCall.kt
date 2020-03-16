package com.example.news.domain.repository

import com.example.news.data.api.Result
import retrofit2.Response
import java.io.IOException

interface SafeApiCall {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        return when (val result = safeApiResult(call, errorMessage)) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>, errorMessage: String
    ): Result<T> {
        val response = call.invoke()
        return if (response.isSuccessful) Result.Success(response.body()!!)
        else Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}