package com.the_b.moviecatalogue

import android.app.Application
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverImpl
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverRepository

class MovieCatalogueApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val apiService = ApiBuilder.createService(ApiService::class.java)
        DiscoverRepository.instance.apply {
            init(DiscoverImpl(apiService))
        }
    }
}