package com.the_b.moviecatalogue.data.repositories.search

import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import com.the_b.moviecatalogue.data.repositories.BaseRepositories
import kotlinx.coroutines.flow.Flow

class SearchRepository private constructor() : BaseRepositories<SearchImpl>() {

    suspend fun searchFilm(query: String, language: String = "en-US"): Flow<FilmModelResponse>? {
        return remoteDataSource?.searchFilm(query, language)
    }

    suspend fun searchTvShow(
        query: String,
        language: String = "en-US"
    ): Flow<TvShowModelResponse>? {
        return remoteDataSource?.searchTvShow(query, language)
    }
}