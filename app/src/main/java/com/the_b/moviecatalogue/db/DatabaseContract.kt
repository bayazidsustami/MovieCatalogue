package com.the_b.moviecatalogue.db

import android.provider.BaseColumns

class DatabaseContract {

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
        }
    }
}