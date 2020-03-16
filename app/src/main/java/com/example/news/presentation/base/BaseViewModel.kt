package com.example.news.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.api.NewsApi
import com.example.news.data.api.RetrofitService
import com.example.news.data.db.Database
import com.example.news.data.repository.Repository
import com.example.news.domain.models.Article
import com.example.news.domain.use_cases.OnFavoriteChangeListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application),
    OnFavoriteChangeListener {

    protected val repository: Repository by lazy {
        Repository(
            application,
            RetrofitService.createService(NewsApi::class.java),
            Database(application).getNewsDao()
        )
    }

    override fun addIntoFavourites(article: Article) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.markAsFavourite(article.id)
        }
    }

    override fun removeFromFavourites(id: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.removeFromFavourites(id)
        }
    }
}