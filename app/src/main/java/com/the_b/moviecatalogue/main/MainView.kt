package com.the_b.moviecatalogue.main

import com.the_b.moviecatalogue.model.FilmModelResponse
import com.the_b.moviecatalogue.model.TvShowModelResponse

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun processFilmData(data: FilmModelResponse)
    fun processTvShowData(data: TvShowModelResponse)
}