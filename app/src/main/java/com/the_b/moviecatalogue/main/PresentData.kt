package com.the_b.moviecatalogue.main

import android.util.Log
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.getLocale
import com.the_b.moviecatalogue.model.FilmModelResponse
import com.the_b.moviecatalogue.model.TvShowModelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresentData(private val view: MainView, private val apiService: ApiService) {

    fun getFilmData(){
        view.showLoading()
        apiService.loadFilm(ApiRepository.API_KEY, getLocale()).enqueue(object : Callback<FilmModelResponse>{
            override fun onFailure(call: Call<FilmModelResponse>, t: Throwable) {
                Log.d("ErrorResponse", "response ---> ${t.message}")
            }

            override fun onResponse(call: Call<FilmModelResponse>, response: Response<FilmModelResponse>) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null) {
                        view.processFilmData(data)
                        Log.d("SuccessResponse", "response ---> ${response.body()}")
                    }
                }
                view.hideLoading()
            }
        })
    }

    fun getTvData(){
        view.showLoading()
        apiService.loadTvShow(ApiRepository.API_KEY, getLocale()).enqueue(object : Callback<TvShowModelResponse>{
            override fun onFailure(call: Call<TvShowModelResponse>, t: Throwable) {
                Log.d("ErrorResponse", "response ---> ${t.message}")
            }

            override fun onResponse(call: Call<TvShowModelResponse>, response: Response<TvShowModelResponse>) {
                if (response.isSuccessful){
                    Log.d("SuccessResponse", "response ---> ${response.body()}")
                    val data = response.body()
                    if (data != null) {
                        view.processTvShowData(data)
                    }
                }
                view.hideLoading()
            }
        })
    }

}