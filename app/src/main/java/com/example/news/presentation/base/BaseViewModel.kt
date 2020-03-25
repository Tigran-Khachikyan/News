package com.example.news.presentation.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.api.NewsApi
import com.example.news.data.api.RetrofitService
import com.example.news.data.db.Database
import com.example.news.data.repository.Repository
import com.example.news.domain.models.ModelDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val repository: Repository by lazy {
        Repository(
            application,
            RetrofitService.createService(NewsApi::class.java),
            Database(application).getNewsDao()
        )
    }

    fun addIntoFavourites(article: ModelDb) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.save(article)
        }
    }

    fun removeFromFavourites(id: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.remove(id)
        }
    }
}