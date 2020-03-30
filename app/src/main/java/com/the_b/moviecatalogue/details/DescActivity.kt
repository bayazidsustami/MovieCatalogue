package com.the_b.moviecatalogue.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.getLocale
import com.the_b.moviecatalogue.main.HomeFragment
import com.the_b.moviecatalogue.model.DescFilmModel
import com.the_b.moviecatalogue.model.FilmModel
import kotlinx.android.synthetic.main.activity_desc.*
import kotlinx.android.synthetic.main.overview_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescActivity : AppCompatActivity() {

    private lateinit var viewModel: DescViewModel

    private lateinit var call: Call<DescFilmModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc)

        val films = intent.getParcelableExtra(HomeFragment.EXTRA_DATA) as FilmModel

        val actionBar = supportActionBar
        actionBar!!.title = films.title
        actionBar.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DescViewModel::class.java)
        viewModel.getDetailsFilm().observe(this, Observer {
            if (it != null){
                showLoading(false)
                showFilm(it)
            }
        })

        val apiService = ApiRepository.createService(ApiService::class.java)
        call = apiService.loadDetailFilm(films.id.toString(), getLocale())

        loadData()
    }

    private fun loadData(){
        showLoading(true)

        if (call.isExecuted){
            return
        }

        call.enqueue(object : Callback<DescFilmModel>{
            override fun onFailure(call: Call<DescFilmModel>, t: Throwable) {
                showLoading(false)
                Log.d(com.the_b.moviecatalogue.TAG, "error ---> ${t.message}")
            }

            override fun onResponse(call: Call<DescFilmModel>, response: Response<DescFilmModel>) {
                showLoading(false)

                val data = response.body() as DescFilmModel
                Log.d(com.the_b.moviecatalogue.TAG, "response ----> $data")
                viewModel.setDetailsFilm(data)
            }
        })
    }

    private fun showFilm(film: DescFilmModel) {
        titleFilm.text = film.title
        overview.text = film.overview
        popularity.text = film.popularity
        status.text = film.status
        voteLabel.text = film.voteAverage
        releaseFilm.text = film.date
        Glide.with(this).load(ApiRepository.IMAGE_URL+film.imageFilm).into(imageFilm)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(state: Boolean){
        if (progressBar != null){
            if(state){
                progressBar.visibility = View.VISIBLE
            }else {
                progressBar.visibility = View.GONE
            }
        }
    }
}
