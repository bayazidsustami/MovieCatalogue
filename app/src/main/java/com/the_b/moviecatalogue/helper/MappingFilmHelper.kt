package com.the_b.moviecatalogue.helper

import android.database.Cursor
import com.the_b.moviecatalogue.db.DatabaseContract
import com.the_b.moviecatalogue.model.local.Films

object MappingFilmHelper {

    fun mapCursorToArrayList(filmCursor: Cursor?): ArrayList<Films>{
        val filmList = ArrayList<Films>()

        filmCursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FilmColumn._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.FilmColumn.TITLE))
                val photo = getString(getColumnIndexOrThrow(DatabaseContract.FilmColumn.PHOTO))
                val overview = getString(getColumnIndexOrThrow(DatabaseContract.FilmColumn.OVERVIEW))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.FilmColumn.DATE))
                val popularity = getString(getColumnIndexOrThrow(DatabaseContract.FilmColumn.POPULARITY))
                val voteAverage = getString(getColumnIndexOrThrow(DatabaseContract.FilmColumn.VOTE_AVERAGE))
                val status = getString(getColumnIndexOrThrow(DatabaseContract.FilmColumn.STATUS))

                filmList.add(Films(
                    id,
                    title,
                    photo,
                    overview,
                    date,
                    popularity,
                    voteAverage,
                    status))
            }
        }

        return filmList
    }
}