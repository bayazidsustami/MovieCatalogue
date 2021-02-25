package com.the_b.moviecatalogue.data.repositories.discover

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.model.FilmModel
import retrofit2.HttpException
import java.io.IOException

class FilmsPagingSource(
    private val apiService: ApiService
): PagingSource<Int, FilmModel>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmModel> {
        val position = params.key ?: STARTING_PAGE_INDEX
        Log.d("PAGING", "POSITION ---> ${params.key}")
        return try {
            val response = apiService.loadFilm(page = position)

            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = position.plus(1)
            )
        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    companion object{
        private const val STARTING_PAGE_INDEX = 1

    }

    override fun getRefreshKey(state: PagingState<Int, FilmModel>): Int? {
        TODO("Not yet implemented")
    }
}