package com.the_b.moviecatalogue.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowModel(
    var id: String? = null,
    var name: String? = null,
    var poster_path: String? = null
): Parcelable