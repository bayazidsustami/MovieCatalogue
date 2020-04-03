package com.the_b.moviecatalogue.model.local

data class TvShows(
    var id: Int,
    var title: String? = null,
    var photo: String? = null,
    var overview: String? = null,
    var date: String? = null,
    var episodes: String? = null,
    var voteAverage: String? = null,
    var status: String? = null
)