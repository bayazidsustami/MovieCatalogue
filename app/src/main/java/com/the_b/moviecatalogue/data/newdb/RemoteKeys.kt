package com.the_b.moviecatalogue.data.newdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey()
    val filmId: Int?,
    val prevKey: Int?,
    val nextKey: Int?
)
