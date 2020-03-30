package com.the_b.moviecatalogue.model

import com.google.gson.annotations.SerializedName

data class DescTvModel(
    var id: Int? = null,

    @SerializedName("name")
    var title: String? = null,

    @SerializedName("poster_path")
    var imageTv: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("number_of_episodes")
    var numEpisode: String? = null,

    @SerializedName("vote_average")
    var voteAverage: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("first_air_date")
    var date: String? = null
)