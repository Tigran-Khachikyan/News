package com.example.news.domain.use_cases

import com.example.news.domain.models.ModelApi
import com.example.news.domain.models.ModelDb


interface OnAdapterFeaturesChangeListener {
    fun openWebFragment(id: String)
    fun addIntoFavourites(article: ModelDb)
    fun removeFromFavourites(id: String)
}