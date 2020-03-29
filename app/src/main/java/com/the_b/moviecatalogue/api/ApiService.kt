package com.the_b.moviecatalogue.api

import com.the_b.moviecatalogue.model.FilmModelResponse
import com.the_b.moviecatalogue.model.TvShowModelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    fun loadFilm(
        //@Query("api_key") api_key: String,
        @Query("language") language: String = "en-US"
    ): Call<FilmModelResponse>

    @GET("discover/tv")
    fun loadTvShow(
        //@Query("api_key") api_key: String,
        @Query("language") language: String = "en-US"
    ): Call<TvShowModelResponse>

    @GET("movie/{id}")
    fun loadDetailFilm(
        @Path("id") id: String,
        //@Query("api_key") api_key: String,
        @Query("language") language: String
    )

    @GET("tv/{id}")
    fun loadDetailTv(
        @Path("id") id: String,
        //@Query("api_key") api_key: String,
        @Query("language") language: String
    )
}