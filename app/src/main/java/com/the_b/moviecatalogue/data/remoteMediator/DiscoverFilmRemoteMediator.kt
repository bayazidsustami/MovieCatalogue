package com.the_b.moviecatalogue.data.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.newdb.DiscoverDatabase
import com.the_b.moviecatalogue.data.newdb.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class DiscoverFilmRemoteMediator(
    private val apiService: ApiService,
    private val discoverDatabase: DiscoverDatabase
): RemoteMediator<Int, FilmModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmModel>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null){
                    throw InvalidObjectException("Remote Keys should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                val prevKey = remoteKeys.prevKey
                if (prevKey == null){
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
        }

        try {
            val apiResponse = apiService.loadFilm(page = page)

            val listFilms = apiResponse.results
            val endOfPaginationReached = listFilms.isEmpty()
            discoverDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    discoverDatabase.remoteKeysDao().clearRemoteKeys()
                    discoverDatabase.filmsDao().clearAllFilms()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = listFilms.map {
                    RemoteKeys(filmId = it.id, prevKey, nextKey)
                }
                discoverDatabase.remoteKeysDao().insertAll(keys)
                discoverDatabase.filmsDao().insertAll(listFilms)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException){
            return MediatorResult.Error(e)
        } catch (e: HttpException){
            return MediatorResult.Error(e)
        }
    }

    //get key for lastItem in branch LoadType.Append
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FilmModel>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { film ->
            film.id?.let { discoverDatabase.remoteKeysDao().remoteKeysFilmId(it) }
        }
    }

    //get key for firstItem in branch LoadType.Prepend
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FilmModel>): RemoteKeys?{
        return state.pages.firstOrNull{it.data.isNotEmpty()}?.data?.firstOrNull()
            ?.let { film ->
                film.id?.let {
                    discoverDatabase.remoteKeysDao().remoteKeysFilmId(it)
                }
            }
    }

    //ket key for first time called loading data
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FilmModel>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { filmId ->
                discoverDatabase.remoteKeysDao().remoteKeysFilmId(filmId)
            }
        }
    }

    companion object{
        private const val STARTING_PAGE_INDEX = 1

    }

}