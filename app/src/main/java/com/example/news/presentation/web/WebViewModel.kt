package com.example.news.presentation.web

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.news.domain.models.ModelDb
import com.example.news.presentation.base.BaseViewModel
import com.example.news.presentation.news.NewsViewModel
import kotlinx.coroutines.Dispatchers

class WebViewModel(application: Application) : BaseViewModel(application) {

    private val models: List<ModelDb>? by lazy { NewsViewModel.mediator.value }

    fun getArticleById(id: String): LiveData<ModelDb?> = liveData(Dispatchers.IO) {
        val result = repository.getSavedArticle(id)
            ?: models?.find { art -> art.id == id }
        emit(result)
    }
}
