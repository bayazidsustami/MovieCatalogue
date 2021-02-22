package com.the_b.moviecatalogue.data.repositories.discover

import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import com.the_b.moviecatalogue.data.repositories.BaseRepositories
import kotlinx.coroutines.flow.Flow

class DiscoverRepository private constructor()
    : BaseRepositories<DiscoverImpl>(){
    suspend fun getFilms(language: String = "en-US"): Flow<FilmModelResponse>? {
        return remoteDataSource?.getListFilm(language)
    }

    suspend fun getTvShows(language: String = "en-US"): Flow<TvShowModelResponse>?{
        return remoteDataSource?.getListTvShow(language)
    }

    companion object{
        val instance by lazy { DiscoverRepository() }
    }
}