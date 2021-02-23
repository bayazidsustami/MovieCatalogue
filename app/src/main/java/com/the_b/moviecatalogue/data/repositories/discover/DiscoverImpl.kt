package com.the_b.moviecatalogue.data.repositories.discover

import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import com.the_b.moviecatalogue.data.repositories.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DiscoverImpl(private val apiService: ApiService): ApiHelper.Discover {
    override suspend fun getListFilm(language: String, page: Int): Flow<FilmModelResponse> {
        return flow {
            val discovers = apiService.loadFilm(language)
            emit(discovers)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getListTvShow(language: String, page: Int): Flow<TvShowModelResponse> {
        return flow {
            val discovers = apiService.loadTvShow(language)
            emit(discovers)
        }.flowOn(Dispatchers.IO)
    }
}