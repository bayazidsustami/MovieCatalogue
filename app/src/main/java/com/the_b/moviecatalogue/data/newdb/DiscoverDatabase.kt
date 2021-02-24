package com.the_b.moviecatalogue.data.newdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.the_b.moviecatalogue.data.model.FilmModel

@Database(
    entities = [FilmModel::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class DiscoverDatabase: RoomDatabase() {
    abstract fun filmsDao(): FilmsDAO
    abstract fun remoteKeysDao(): RemoteKeysDAO

    companion object{
        @Volatile
        private var INSTANCE: DiscoverDatabase? = null

        fun getInstance(context: Context): DiscoverDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE
                    ?: buildDatabase(context)
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                DiscoverDatabase::class.java, "Discover.db")
                .build()
    }
}