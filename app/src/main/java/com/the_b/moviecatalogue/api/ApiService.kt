package com.the_b.moviecatalogue.api

import com.the_b.moviecatalogue.data.model.DescFilmModel
import com.the_b.moviecatalogue.data.model.DescTvModel
import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun loadFilm(
        @Query("language") language: String = "en-US"
    ): FilmModelResponse

    @GET("discover/tv")
    suspend fun loadTvShow(
        @Query("language") language: String = "en-US"
    ): TvShowModelResponse

    @GET("movie/{id}")
    fun loadDetailFilm(
        @Path("id") id: String,
        @Query("language") language: String
    ): Call<DescFilmModel>

    @GET("tv/{id}")
    fun loadDetailTv(
        @Path("id") id: String,
        @Query("language") language: String
    ): Call<DescTvModel>

    @GET("search/movie")
    fun searchFilm(
        @Query("query") query: String,
        @Query("language") language: String
    ): Call<FilmModelResponse>

    @GET("search/tv")
    fun searchTv(
        @Query("query") query: String,
        @Query("language") language: String
    ): Call<TvShowModelResponse>

    @GET("discover/movie")
    fun releaseFilm(
        @Query("primary_release_date.gte") gte: String,
        @Query("primary_release_date.lte") ite: String
    ): Call<FilmModelResponse>
}