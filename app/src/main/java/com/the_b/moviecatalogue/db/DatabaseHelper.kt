package com.the_b.moviecatalogue.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.the_b.moviecatalogue.db.DatabaseContract.FilmColumn.Companion.TABLE_FILM
import com.the_b.moviecatalogue.db.DatabaseContract.TvShowColumn.Companion.TABLE_TV

internal class DatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "movie_catalogue"
        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_FILM = "CREATE TABLE $TABLE_FILM" +
                "(${DatabaseContract.FilmColumn._ID} TEXT PRIMARY KEY," +
                "${DatabaseContract.FilmColumn.PHOTO} TEXT NOT NULL," +
                "${DatabaseContract.FilmColumn.TITLE} TEXT NOT NULL," +
                "${DatabaseContract.FilmColumn.OVERVIEW} TEXT NOT NULL," +
                "${DatabaseContract.FilmColumn.DATE} TEXT NOT NULL," +
                "${DatabaseContract.FilmColumn.POPULARITY} TEXT NOT NULL," +
                "${DatabaseContract.FilmColumn.VOTE_AVERAGE} TEXT NOT NULL," +
                "${DatabaseContract.FilmColumn.STATUS} TEXT NOT NULL)"

        private val SQL_CREATE_TABLE_TVSHOW = "CREATE TABLE $TABLE_TV" +
                "(${DatabaseContract.TvShowColumn._ID} TEXT PRIMARY KEY," +
                "${DatabaseContract.TvShowColumn.PHOTO} TEXT NOT NULL," +
                "${DatabaseContract.TvShowColumn.TITLE} TEXT NOT NULL," +
                "${DatabaseContract.TvShowColumn.OVERVIEW} TEXT NOT NULL," +
                "${DatabaseContract.TvShowColumn.DATE} TEXT NOT NULL," +
                "${DatabaseContract.TvShowColumn.EPISODES} TEXT NOT NULL," +
                "${DatabaseContract.TvShowColumn.VOTE_AVERAGE} TEXT NOT NULL," +
                "${DatabaseContract.TvShowColumn.STATUS} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FILM)
        db.execSQL(SQL_CREATE_TABLE_TVSHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FILM")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TV")
        onCreate(db)
    }
}