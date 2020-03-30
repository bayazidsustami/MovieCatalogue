package com.the_b.moviecatalogue.model

import com.google.gson.annotations.SerializedName

data class DescFilmModel(
    var id: Int? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("poster_path")
    var imageFilm: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("popularity")
    var popularity: String? = null,

    @SerializedName("vote_average")
    var voteAverage: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("release_date")
    var date: String? = null
)