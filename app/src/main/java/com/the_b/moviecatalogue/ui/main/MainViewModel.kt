package com.the_b.moviecatalogue.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.model.TvShowModel
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverPagingRepository
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MainViewModel(
    private val pagingRepository: DiscoverPagingRepository
): ViewModel() {

    private var currentFilmResult: Flow<PagingData<FilmModel>>? = null
    private var currentTvShowResult: Flow<PagingData<TvShowModel>>? = null

    fun getListFilmPaging(): Flow<PagingData<FilmModel>>{
        val lastResult = currentFilmResult
        if (lastResult != null){
            return lastResult
        }
        val newResult: Flow<PagingData<FilmModel>> =
            pagingRepository.getResultFilmStream().cachedIn(viewModelScope)
        currentFilmResult = newResult
        return newResult
    }

    fun getListTvPaging(): Flow<PagingData<TvShowModel>>{
        val lastResult = currentTvShowResult
        if (lastResult != null){
            return lastResult
        }
        val newResult: Flow<PagingData<TvShowModel>> =
            pagingRepository.getResultTvStream().cachedIn(viewModelScope)
        currentTvShowResult = newResult
        return newResult
    }
}