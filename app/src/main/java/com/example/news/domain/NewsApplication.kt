package com.example.news.domain

import android.app.Application
import com.example.news.domain.di.recyclerAdapterModule
import com.example.news.domain.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
          //  modules(listOf(viewModelModule, recyclerAdapterModule))
        }
    }
}