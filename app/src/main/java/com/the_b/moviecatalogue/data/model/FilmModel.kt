package com.the_b.moviecatalogue.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmModel(
    var id: Int? = null,
    var title: String? = null,
    var poster_path: String? = null
): Parcelable