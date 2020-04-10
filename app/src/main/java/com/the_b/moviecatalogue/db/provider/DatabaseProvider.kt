package com.the_b.moviecatalogue.db.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.the_b.moviecatalogue.db.DatabaseContract.AUTHORITY
import com.the_b.moviecatalogue.db.DatabaseContract.FilmColumn.Companion.CONTENT_URI_FILM
import com.the_b.moviecatalogue.db.DatabaseContract.FilmColumn.Companion.TABLE_FILM
import com.the_b.moviecatalogue.db.DatabaseContract.TvShowColumn.Companion.TABLE_TV
import com.the_b.moviecatalogue.db.FilmHelper
import com.the_b.moviecatalogue.db.TvShowHelper

class DatabaseProvider : ContentProvider() {

    companion object{
        private const val FILM_DIR = 1
        private const val FILM_ID = 2
        private const val TV_DIR = 3
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var filmHelper: FilmHelper
        private lateinit var tvShowHelper: TvShowHelper

        init{
            sUriMatcher.addURI(AUTHORITY, TABLE_FILM, FILM_DIR)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_FILM/#", FILM_ID)
            sUriMatcher.addURI(AUTHORITY, TABLE_TV, TV_DIR)
        }
    }

    override fun onCreate(): Boolean {
        filmHelper = FilmHelper.getInstance(context as Context)

        filmHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when(sUriMatcher.match(uri)){
            FILM_DIR -> cursor = filmHelper.queryAll()
            FILM_ID -> cursor = filmHelper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }

        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when(FILM_DIR){
            sUriMatcher.match(uri) -> filmHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI_FILM, null)
        return Uri.parse("$CONTENT_URI_FILM/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val update: Int = when(FILM_ID){
            sUriMatcher.match(uri) -> filmHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI_FILM, null)

        return update
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when(FILM_ID){
            sUriMatcher.match(uri) -> filmHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_FILM, null)
        return deleted
    }
}
