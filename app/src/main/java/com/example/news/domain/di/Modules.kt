package com.example.news.domain.di

import com.example.news.domain.models.ModelApi
import com.example.news.domain.use_cases.OnAdapterFeaturesChangeListener
import com.example.news.presentation.RecyclerViewAdapter
import com.example.news.presentation.favourites.FavouritesViewModel
import com.example.news.presentation.news.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    factory { FavouritesViewModel(androidApplication()) }
    factory { NewsViewModel(androidApplication()) }
}


val recyclerAdapterModule: Module = module {
    factory { listOf<ModelApi>() }
    factory { OnAdapterFeaturesChangeListener::class.java }
    factory { RecyclerViewAdapter(get(), get()) }
}