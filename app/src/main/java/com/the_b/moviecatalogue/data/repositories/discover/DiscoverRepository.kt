package com.the_b.moviecatalogue.data.repositories.discover

import com.the_b.moviecatalogue.data.model.FilmModelResponse
import com.the_b.moviecatalogue.data.model.TvShowModelResponse
import com.the_b.moviecatalogue.data.repositories.BaseRepositories
import kotlinx.coroutines.flow.Flow

class DiscoverRepository private constructor()
    : BaseRepositories<DiscoverImpl>(){
    suspend fun getFilms(language: String = "en-US", page: Int = 1): Flow<FilmModelResponse>? {
        return remoteDataSource?.getListFilm(language, page)
    }

    suspend fun getTvShows(language: String = "en-US", page: Int = 1): Flow<TvShowModelResponse>?{
        return remoteDataSource?.getListTvShow(language, page)
    }

    companion object{
        val instance by lazy { DiscoverRepository() }
    }
}