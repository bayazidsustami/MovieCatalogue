package com.the_b.moviecatalogue.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.the_b.moviecatalogue.db.DatabaseContract.FilmColumn.Companion.TABLE_FILM
import com.the_b.moviecatalogue.db.DatabaseContract.FilmColumn.Companion._ID
import java.sql.SQLException

class FilmHelper(context: Context) {

    companion object{
        private const val DATABASE_TABLE = TABLE_FILM
        private lateinit var databaseHelper: DatabaseHelper

        private var INSTANCE: FilmHelper? = null
        fun getInstance(context: Context): FilmHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: FilmHelper(context)
            }

        private lateinit var database:SQLiteDatabase
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()

        if (database.isOpen){
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }

    fun insert(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}