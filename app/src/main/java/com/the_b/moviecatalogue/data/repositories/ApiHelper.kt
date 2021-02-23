package com.the_b.moviecatalogue.data.repositories

import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    interface Discover{
        suspend fun getListFilm(language: String = "en-US", page: Int): Flow<FilmModelResponse>
        suspend fun getListTvShow(language: String = "en-US", page: Int): Flow<TvShowModelResponse>
    }

    interface Search{
        suspend fun searchFilm(query: String, language: String = "en-US"): Flow<FilmModelResponse>
        suspend fun searchTvShow(query: String, language: String = "en-US"): Flow<TvShowModelResponse>
    }
}