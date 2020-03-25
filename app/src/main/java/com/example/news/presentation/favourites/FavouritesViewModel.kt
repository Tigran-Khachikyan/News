package com.example.news.presentation.favourites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.news.domain.models.ModelApi
import com.example.news.domain.models.ModelDb
import com.example.news.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(application: Application) : BaseViewModel(application) {

    val favouriteNews: LiveData<List<ModelDb>> = repository.getFavourites()
    fun clearFavourites() = viewModelScope.launch(Dispatchers.Default){
        repository.clearAll()
    }
}
