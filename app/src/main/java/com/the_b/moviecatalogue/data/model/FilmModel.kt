package com.the_b.moviecatalogue.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "films")
@Parcelize
data class FilmModel(
    @PrimaryKey
    var id: Int? = null,

    var title: String? = null,

    var poster_path: String? = null
): Parcelable