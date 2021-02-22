package com.the_b.moviecatalogue.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the_b.moviecatalogue.utilities.TAG
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.utilities.getLocale
import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private var listFilm = MutableLiveData<FilmModelResponse>()
    private var listTvShow = MutableLiveData<TvShowModelResponse>()

    fun setFilm(){

    }

    fun setTvShow(){

    }

    fun setSearchFilm(query: String){
        val apiService = ApiBuilder.createService(ApiService::class.java)
        val call = apiService.searchFilm(query, getLocale())
        call.enqueue(object : Callback<FilmModelResponse>{
            override fun onFailure(call: Call<FilmModelResponse>, t: Throwable) {
                Log.d(TAG, "Response ---> ${t.message}")
            }

            override fun onResponse(
                call: Call<FilmModelResponse>,
                response: Response<FilmModelResponse>
            ) {
                val data = response.body()
                listFilm.postValue(data)
            }
        })
    }

    fun setSearchTv(query: String){
        val apiService = ApiBuilder.createService(ApiService::class.java)
        val call = apiService.searchTv(query, getLocale())
        call.enqueue(object : Callback<TvShowModelResponse>{
            override fun onFailure(call: Call<TvShowModelResponse>, t: Throwable) {
                Log.d(TAG, "Response ----> ${t.message}")
            }

            override fun onResponse(
                call: Call<TvShowModelResponse>,
                response: Response<TvShowModelResponse>
            ) {
                val data = response.body()
                listTvShow.postValue(data)
            }
        })
    }

    fun getFilms(): LiveData<FilmModelResponse>{
        return listFilm
    }

    fun getTvShow(): LiveData<TvShowModelResponse>{
        return listTvShow
    }

    fun getSearchFilm(): LiveData<FilmModelResponse>{
        return listFilm
    }

    fun getSearchTv(): LiveData<TvShowModelResponse>{
        return listTvShow
    }
}