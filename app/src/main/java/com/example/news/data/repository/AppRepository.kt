package com.example.news.data.repository

import androidx.lifecycle.LiveData
import com.example.news.domain.models.ModelApi
import com.example.news.domain.models.ModelDb

interface AppRepository {
    fun getSavedArticle(id: String): ModelDb?
    fun getFavourites(): LiveData<List<ModelDb>>
    suspend fun getNewsApi(page:Int): List<ModelApi>?
    // suspend fun updateDb()
    suspend fun save(article: ModelDb)
    suspend fun remove(id: String)
    suspend fun clearAll()
}