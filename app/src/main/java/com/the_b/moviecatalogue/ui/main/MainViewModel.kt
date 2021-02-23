package com.the_b.moviecatalogue.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModel
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverPagingRepository
import com.the_b.moviecatalogue.utilities.Resource
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val pagingRepository: DiscoverPagingRepository
): ViewModel() {

    private var listFilm = MutableLiveData<Resource<FilmModelResponse>>()
    private var listTvShow = MutableLiveData<Resource<TvShowModelResponse>>()

    fun setSearchFilm(query: String){

    }

    fun setSearchTv(query: String){

    }

    fun getSearchFilm(): LiveData<Resource<FilmModelResponse>>{
        return listFilm
    }

    fun getSearchTv(): LiveData<Resource<TvShowModelResponse>>{
        return listTvShow
    }

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