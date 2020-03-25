package com.example.news.presentation.news

import android.app.Application
import androidx.lifecycle.*
import com.example.news.domain.models.ModelApi
import com.example.news.domain.models.ModelDb
import com.example.news.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(application: Application) : BaseViewModel(application) {

    private val articles: MutableLiveData<ArrayList<ModelDb>> = MutableLiveData()
    private val favourites: LiveData<List<ModelDb>> = repository.getFavourites()

    companion object {
        val mediator: MediatorLiveData<List<ModelDb>> = MediatorLiveData()
    }

    init {
        callNextPageNews(1)
    }

    fun callNextPageNews(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val news = transform(repository.getNewsApi(page))
            val value = articles.value ?: arrayListOf()
            value.addAll(news)
            withContext(Dispatchers.Main) {
                articles.value = value
            }
        }
    }

    fun getNews(): LiveData<List<ModelDb>?> {
        mediator.addSource(articles) {
            mediator.value = combine(articles, favourites)
        }
        mediator.addSource(favourites) {
            mediator.value = combine(articles, favourites)
        }
        return mediator
    }

    private fun transform(news: List<ModelApi>?): List<ModelDb> {
        return news?.map { modelApi ->
            ModelDb(
                modelApi.id,
                modelApi.webPublicationDate,
                modelApi.webUrl,
                modelApi.sectionName,
                modelApi.webTitle,
                false
            )
        }?: listOf()
    }

    private fun combine(
        _news: LiveData<ArrayList<ModelDb>>, _saved: LiveData<List<ModelDb>>
    ): List<ModelDb>? {

        val news = _news.value
        news?.forEach { a -> a.isFavourite = false }

        val saved = _saved.value

        if (news != null && saved != null) {
            saved.forEach { s ->
                for (n in news.indices) {
                    if (s.id == news[n].id) {
                        news[n].isFavourite = true
                        break
                    }
                }
            }
        }
        return news
    }

    fun cancel() {
        mediator.removeSource(articles)
        mediator.removeSource(favourites)
    }

}
