package com.the_b.moviecatalogue.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverRepository
import com.the_b.moviecatalogue.utilities.Resource
import com.the_b.moviecatalogue.utilities.getLocale
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DiscoverRepository): ViewModel() {

    private var listFilm = MutableLiveData<Resource<FilmModelResponse>>()
    private var listTvShow = MutableLiveData<Resource<TvShowModelResponse>>()

    fun setFilm(){
        viewModelScope.launch {
            repository.getFilms(getLocale())
                ?.onStart {
                    listFilm.postValue(Resource.loading(data = null))
                }
                ?.catch {
                    listFilm.postValue(Resource.failed(data = null, message = "Error"))
                }
                ?.collect {
                    listFilm.postValue(Resource.success(data = it))
                }
        }
    }

    fun setTvShow(){
        viewModelScope.launch {
            repository.getTvShows(getLocale())
                ?.onStart {
                    listTvShow.postValue(Resource.loading(data = null))
                }
                ?.catch {
                    listTvShow.postValue(Resource.failed(data = null, message = "Error"))
                }
                ?.collect {
                    listTvShow.postValue(Resource.success(data = it))
                }
        }
    }

    fun setSearchFilm(query: String){

    }

    fun setSearchTv(query: String){

    }

    fun getFilms(): LiveData<Resource<FilmModelResponse>>{
        return listFilm
    }

    fun getTvShow(): LiveData<Resource<TvShowModelResponse>>{
        return listTvShow
    }

    fun getSearchFilm(): LiveData<Resource<FilmModelResponse>>{
        return listFilm
    }

    fun getSearchTv(): LiveData<Resource<TvShowModelResponse>>{
        return listTvShow
    }
}