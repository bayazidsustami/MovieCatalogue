package com.the_b.moviecatalogue

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.newdb.DiscoverDatabase
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverPagingRepository

@ExperimentalPagingApi
object Injection {
    fun provideDiscoverRepository(context: Context): DiscoverPagingRepository{
        return DiscoverPagingRepository(
            ApiBuilder.createService(ApiService::class.java),
            DiscoverDatabase.getInstance(context)
        )
    }
}