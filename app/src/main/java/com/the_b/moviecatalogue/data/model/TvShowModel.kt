package com.the_b.moviecatalogue.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tv_show")
@Parcelize
data class TvShowModel(
    @PrimaryKey
    var id: String? = null,

    var name: String? = null,

    var poster_path: String? = null
): Parcelable