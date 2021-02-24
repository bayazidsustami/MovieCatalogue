package com.the_b.moviecatalogue.data.repositories.discover

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.model.TvShowModel
import com.the_b.moviecatalogue.data.newdb.DiscoverDatabase
import com.the_b.moviecatalogue.data.remoteMediator.DiscoverFilmRemoteMediator
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class DiscoverPagingRepository(
    private val apiService: ApiService,
    private val database: DiscoverDatabase
) {
    fun getResultFilmStream(): Flow<PagingData<FilmModel>>{
        val pagingSourceFactory = {database.filmsDao().getFilm()}
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = DiscoverFilmRemoteMediator(
                apiService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getResultTvStream(): Flow<PagingData<TvShowModel>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {TvShowPagingSource(apiService)}
        ).flow
    }

    companion object{

    }
}