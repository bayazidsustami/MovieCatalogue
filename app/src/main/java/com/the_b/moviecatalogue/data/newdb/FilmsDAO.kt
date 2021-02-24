package com.the_b.moviecatalogue.data.newdb

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.the_b.moviecatalogue.data.model.FilmModel

@Dao
interface FilmsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films: List<FilmModel>)

    @Query("SELECT * FROM films ORDER BY title ASC")
    fun getFilm(): PagingSource<Int, FilmModel>

    @Query("DELETE FROM films")
    suspend fun clearAllFilms()
}