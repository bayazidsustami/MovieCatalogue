package com.the_b.moviecatalogue.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverRepository

@Suppress("UNCHECKED_CAST")
class MainVMFactory(private val discoverRepository: DiscoverRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(discoverRepository) as T

        throw IllegalArgumentException()
    }
}