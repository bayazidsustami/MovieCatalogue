package com.the_b.moviecatalogue.data.repositories.discover

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.model.TvShowModel
import retrofit2.HttpException
import java.io.IOException

class TvShowPagingSource(
    private val apiService: ApiService
): PagingSource<Int, TvShowModel>() {

    override fun getRefreshKey(state: PagingState<Int, TvShowModel>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowModel> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.loadTvShow(page = position)

            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = position.plus(1)
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    companion object{
        private const val STARTING_PAGE_INDEX = 1
    }
}