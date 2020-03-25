package com.example.news.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.news.R
import com.example.news.data.api.NewsApi
import com.example.news.data.api.hasNetwork
import com.example.news.data.db.NewsDao
import com.example.news.domain.models.ModelApi
import com.example.news.domain.models.ModelDb

class Repository(
    private val context: Context,
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : SafeApiCall, AppRepository {

    /* override suspend fun updateDb() {

         if (hasNetwork(context)) {
             val newsResponse = safeApiCall(
                 call = { newsApi.getNewsAsync().await() },
                 errorMessage = context.getString(R.string.error)
             )
             val articles = newsResponse?.response?.results
             if (articles != null && articles.isNotEmpty()) {
                 newsDao.clearAll()
                 newsDao.insert(articles)
             }
         }
     }*/

    override suspend fun getNewsApi(page: Int): List<ModelApi>? {
        var articles: List<ModelApi>? = null
        if (hasNetwork(context)) {
            val newsResponse = safeApiCall(
                call = { newsApi.getNewsAsync(page).await() },
                errorMessage = context.getString(R.string.error)
            )
            newsResponse?.response?.results?.let { articles = it }
        }
        return articles
    }

    override fun getSavedArticle(id: String): ModelDb? = newsDao.getArticle(id)

    override fun getFavourites(): LiveData<List<ModelDb>> = newsDao.getAll()

    override suspend fun save(article: ModelDb) = newsDao.insert(article)

    override suspend fun remove(id: String) = newsDao.removeFavourite(id)

    override suspend fun clearAll() = newsDao.clearAll()
}
