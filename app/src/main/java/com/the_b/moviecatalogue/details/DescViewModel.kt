package com.the_b.moviecatalogue.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.getLocale
import com.the_b.moviecatalogue.model.DescFilmModel
import com.the_b.moviecatalogue.model.DescTvModel
import retrofit2.Call

class DescViewModel: ViewModel() {

    private val film = MutableLiveData<DescFilmModel>()
    private val tv = MutableLiveData<DescTvModel>()

    fun setDetailsFilm(id: DescFilmModel){
        film.postValue(id)
    }

    fun getDetailsFilm(): LiveData<DescFilmModel>{
        return film
    }

    fun setDetailsTv(id: DescTvModel){
        tv.postValue(id)
    }

    fun getDetailsTv(): LiveData<DescTvModel>{
        return tv
    }
}