package com.the_b.moviecatalogue.data.model

import com.google.gson.annotations.SerializedName

data class FilmModelResponse(
    @SerializedName("page")
    val page:Int,

    @SerializedName("results")
    val results: List<FilmModel>,

    @SerializedName("total_pages")
    val totalPage: Int,

    @SerializedName("total_results")
    val totalResult: Int
)