package com.the_b.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmModel(
    var id: String? = null,
    var title: String? = null,
    var poster_path: String? = null
): Parcelable