package com.the_b.moviecatalogue

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmModel(
    var title: String,
    var desc: String,
    var director: String,
    var years: String,
    var imgFilm: Int
): Parcelable