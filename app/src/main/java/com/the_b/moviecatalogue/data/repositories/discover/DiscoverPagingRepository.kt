package com.the_b.moviecatalogue.data.repositories.discover

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.model.FilmModel
import kotlinx.coroutines.flow.Flow

class DiscoverPagingRepository(
    private val apiService: ApiService
) {
    fun getResultFilmStream(): Flow<PagingData<FilmModel>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {FilmsPagingSource(apiService)}
        ).flow
    }


}