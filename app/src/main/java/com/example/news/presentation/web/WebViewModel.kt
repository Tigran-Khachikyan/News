package com.example.news.presentation.web

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.news.domain.models.Article
import com.example.news.presentation.base.BaseViewModel

class WebViewModel(application: Application) : BaseViewModel(application) {

    fun getArticleById(id: String): LiveData<Article> = repository.getArticle(id)
}
