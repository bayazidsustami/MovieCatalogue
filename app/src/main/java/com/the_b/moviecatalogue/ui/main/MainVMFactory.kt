package com.the_b.moviecatalogue.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverPagingRepository

@ExperimentalPagingApi
@Suppress("UNCHECKED_CAST")
class MainVMFactory(
    private val pagingRepository: DiscoverPagingRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(pagingRepository) as T

        throw IllegalArgumentException()
    }
}