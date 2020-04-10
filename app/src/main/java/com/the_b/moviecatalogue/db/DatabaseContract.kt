package com.the_b.moviecatalogue.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.the_b.moviecatalogue"
    const val SCHEME = "content"

    internal class FilmColumn: BaseColumns{
        companion object{
            const val TABLE_FILM = "film"
            const val _ID = "_id"
            const val PHOTO = "photo"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val DATE = "date"
            const val POPULARITY = "popularity"
            const val VOTE_AVERAGE = "vote_average"
            const val STATUS = "status"

            val CONTENT_URI_FILM: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FILM)
                .build()
        }
    }

    internal class TvShowColumn: BaseColumns {
        companion object{
            const val TABLE_TV = "tv_show"
            const val _ID = "_id"
            const val PHOTO = "photo"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val DATE = "date"
            const val EPISODES = "episode"
            const val VOTE_AVERAGE = "vote_average"
            const val STATUS = "status"

            val CONTENT_URI_TV: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build()
        }
    }
}