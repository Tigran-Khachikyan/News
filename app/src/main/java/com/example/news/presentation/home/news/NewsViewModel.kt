package com.example.news.presentation.home.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.news.data.api.NewsApi
import com.example.news.data.api.RetrofitService
import com.example.news.data.db.Database
import com.example.news.data.repository.Repository
import com.example.news.domain.models.Article
import com.example.news.domain.use_cases.OnFavoriteChangeListener
import com.example.news.presentation.home.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : BaseViewModel(application) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDb()
        }
    }

     val news: LiveData<List<Article>> = repository.getNews()
}
