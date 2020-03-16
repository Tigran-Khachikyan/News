package com.example.news.presentation.favourites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.news.domain.models.Article
import com.example.news.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(application: Application) : BaseViewModel(application) {

    val favouriteNews: LiveData<List<Article>> = repository.getFavourites()
    fun clearFavourites() = viewModelScope.launch(Dispatchers.Default){
        repository.clearAllFavourites()
    }
}
