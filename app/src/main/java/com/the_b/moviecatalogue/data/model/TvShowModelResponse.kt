package com.the_b.moviecatalogue.data.model

import com.google.gson.annotations.SerializedName

data class TvShowModelResponse(
    @SerializedName("page")
    val page:Int,

    @SerializedName("results")
    val results: List<TvShowModel>,

    @SerializedName("total_pages")
    val totalPage: Int,

    @SerializedName("total_results")
    val totalResult: Int
)