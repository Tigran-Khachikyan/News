package com.example.news.presentation.home.web

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.news.domain.models.Article
import com.example.news.presentation.home.BaseViewModel

class WebViewModel(application: Application) : BaseViewModel(application) {

    fun getArticleById(id: String): LiveData<Article> = repository.getArticle(id)
}
