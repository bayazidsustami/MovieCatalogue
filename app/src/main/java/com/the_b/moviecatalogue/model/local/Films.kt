package com.the_b.moviecatalogue.model.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Films(
    var id: Int,
    var title: String?,
    var photo: String?,
    var overview: String?,
    var date: String?,
    var popularity: String?,
    var voteAverage: String?,
    var status: String?
): Parcelable