package com.example.news.domain.use_cases

import com.example.news.domain.models.Article


interface OnFavoriteChangeListener {
    fun addIntoFavourites(article: Article)
    fun removeFromFavourites(id: String)
}