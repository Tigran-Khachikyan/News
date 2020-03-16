package com.example.news.presentation.news

import android.app.Application
import androidx.lifecycle.*
import com.example.news.domain.models.Article
import com.example.news.presentation.base.BaseViewModel
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
