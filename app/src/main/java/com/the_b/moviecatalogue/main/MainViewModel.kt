package com.the_b.moviecatalogue.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the_b.moviecatalogue.model.FilmModelResponse
import com.the_b.moviecatalogue.model.TvShowModelResponse

class MainViewModel: ViewModel() {

    private var listFilm = MutableLiveData<FilmModelResponse>()
    private var listTvShow = MutableLiveData<TvShowModelResponse>()

    fun setFilm(data: FilmModelResponse){
        listFilm.postValue(data)
    }

    fun setTvShow(data: TvShowModelResponse){
        listTvShow.postValue(data)
    }

    fun getFilms(): LiveData<FilmModelResponse>{
        return listFilm
    }

    fun getTvShow(): LiveData<TvShowModelResponse>{
        return listTvShow
    }
}