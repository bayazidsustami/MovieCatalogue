package com.the_b.moviecatalogue.data.repositories.search

import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import com.the_b.moviecatalogue.data.repositories.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchImpl(private val api: ApiService): ApiHelper.Search {

    override suspend fun searchFilm(query: String, language: String): Flow<FilmModelResponse> {
        return flow {
            val data = api.searchFilm(query, language)
            emit(data)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun searchTvShow(query: String, language: String): Flow<TvShowModelResponse> {
        return flow {
            val data = api.searchTv(query, language)
            emit(data)
        }.flowOn(Dispatchers.IO)
    }
}