package com.example.news.domain.repository

import androidx.lifecycle.LiveData
import com.example.news.domain.models.Article

interface AppRepository {
    fun getNews(): LiveData<List<Article>>
    fun getArticle(id: String): LiveData<Article>
    fun getFavourites(): LiveData<List<Article>>
    suspend fun updateDb()
    suspend fun markAsFavourite(id: String)
    suspend fun removeFromFavourites(id: String)
    suspend fun clearAllFavourites()
}