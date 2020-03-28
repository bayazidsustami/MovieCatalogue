package com.the_b.moviecatalogue.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "a81e9d88885bc612f8202ca5bf308441"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/original"

    fun create(): ApiService{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}