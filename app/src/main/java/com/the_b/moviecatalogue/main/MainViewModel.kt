package com.the_b.moviecatalogue.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the_b.moviecatalogue.TAG
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.getLocale
import com.the_b.moviecatalogue.model.FilmModelResponse
import com.the_b.moviecatalogue.model.TvShowModelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private var listFilm = MutableLiveData<FilmModelResponse>()
    private var listTvShow = MutableLiveData<TvShowModelResponse>()

    fun setFilm(){
        val apiService = ApiRepository.createService(ApiService::class.java)
        val call = apiService.loadFilm(getLocale())
        call.enqueue(object : Callback<FilmModelResponse>{
            override fun onFailure(call: Call<FilmModelResponse>, t: Throwable) {
                Log.d(TAG, "response ----> ${t.message}")
            }

            override fun onResponse(call: Call<FilmModelResponse>, response: Response<FilmModelResponse>) {
               if (response.isSuccessful){
                   val data = response.body()
                   Log.d(TAG, "response ---> $data")
                   listFilm.postValue(data)
               }
            }
        })
    }

    fun setTvShow(){
        val apiService = ApiRepository.createService(ApiService::class.java)
        val call = apiService.loadTvShow(getLocale())
        call.enqueue(object : Callback<TvShowModelResponse>{
            override fun onFailure(call: Call<TvShowModelResponse>, t: Throwable) {
                Log.d(TAG, "response ----> ${t.message}")
            }

            override fun onResponse(
                call: Call<TvShowModelResponse>,
                response: Response<TvShowModelResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    Log.d(TAG, "response ----> $data")
                    listTvShow.postValue(data)
                }
            }
        })
    }

    fun setSearchFilm(query: String){
        val apiService = ApiRepository.createService(ApiService::class.java)
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
        val apiService = ApiRepository.createService(ApiService::class.java)
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