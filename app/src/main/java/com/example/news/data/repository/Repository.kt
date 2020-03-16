package com.example.news.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.news.data.api.NewsApi
import com.example.news.data.api.hasNetwork
import com.example.news.data.db.NewsDao
import com.example.news.domain.models.Article
import com.example.news.domain.repository.AppRepository
import com.example.news.domain.repository.SafeApiCall

class Repository(
    private val context: Context,
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : SafeApiCall, AppRepository {

    override suspend fun updateDb() {

        if (hasNetwork(context)) {
            val newsResponse = safeApiCall(
                call = { newsApi.getNewsAsync().await() },
                errorMessage = "Error Fetching News"
            )
            val articles = newsResponse?.response?.results
            if (articles != null && articles.isNotEmpty()) {
                newsDao.clear()
                newsDao.insert(articles)
            }
        }
    }

    override fun getArticle(id: String): LiveData<Article> = newsDao.getArticle(id)

    override fun getNews(): LiveData<List<Article>> = newsDao.getAll()

    override fun getFavourites(): LiveData<List<Article>> = newsDao.getFavourites()

    override suspend fun markAsFavourite(id: String) = newsDao.setFavourite(id)

    override suspend fun removeFromFavourites(id: String) = newsDao.removeFavourite(id)

    override suspend fun clearAllFavourites() = newsDao.removeAllFavourites()
}
