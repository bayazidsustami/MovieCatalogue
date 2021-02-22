package com.the_b.moviecatalogue.helper

import android.database.Cursor
import com.the_b.moviecatalogue.db.DatabaseContract
import com.the_b.moviecatalogue.data.model.local.TvShows

object MappingTvShowHelper {

    fun mapToCursorArrayList(tvCursor: Cursor?): ArrayList<TvShows>{
        val tvList = ArrayList<TvShows>()

        tvCursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.TvShowColumn._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.TvShowColumn.TITLE))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.TvShowColumn.PHOTO))
                val overview = getString(getColumnIndexOrThrow(DatabaseContract.TvShowColumn.OVERVIEW))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.TvShowColumn.DATE))
                val episodes = getString(getColumnIndexOrThrow(DatabaseContract.TvShowColumn.EPISODES))
                val voteAverage = getString(getColumnIndexOrThrow(DatabaseContract.TvShowColumn.VOTE_AVERAGE))
                val status = getString(getColumnIndexOrThrow(DatabaseContract.TvShowColumn.STATUS))

                tvList.add(
                    TvShows(
                        id,
                        title,
                        photo,
                        overview,
                        date,
                        episodes,
                        voteAverage,
                        status
                ))
            }
        }
        return tvList
    }
}