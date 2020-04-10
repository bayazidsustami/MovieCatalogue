package com.the_b.moviecatalogue.details

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.db.DatabaseContract
import com.the_b.moviecatalogue.db.FilmHelper
import com.the_b.moviecatalogue.getLocale
import com.the_b.moviecatalogue.model.DescFilmModel
import com.the_b.moviecatalogue.model.FilmModel
import com.the_b.moviecatalogue.model.local.Films
import kotlinx.android.synthetic.main.activity_desc.*
import kotlinx.android.synthetic.main.overview_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescActivity : AppCompatActivity(){

    private lateinit var viewModel: DescViewModel

    private lateinit var call: Call<DescFilmModel>

    private lateinit var filmHelper: FilmHelper
    private lateinit var details: DescFilmModel
    private var film: Films? = null

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc)

        filmHelper = FilmHelper.getInstance(applicationContext)
        filmHelper.open()

        val films = intent.getParcelableExtra(EXTRA_DATA) as FilmModel

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

                details = DescFilmModel(
                    data.id,
                    data.title,
                    data.imageFilm,
                    data.overview,
                    data.popularity,
                    data.voteAverage,
                    data.status,
                    data.date
                )
            }
        })



        btn_addFav.setOnClickListener{
            val id = details.id!!
            val title = details.title
            val photo = details.imageFilm
            val overview = details.overview
            val popularity = details.popularity
            val date = details.date
            val status = details.status
            val voteAverage = details.voteAverage

            film?.id = id
            film?.title = title
            film?.photo = photo
            film?.overview = overview
            film?.popularity = popularity
            film?.date = date
            film?.status = status
            film?.voteAverage = voteAverage

            val values = ContentValues()
            values.put(DatabaseContract.FilmColumn._ID, id)
            values.put(DatabaseContract.FilmColumn.TITLE, title)
            values.put(DatabaseContract.FilmColumn.PHOTO, photo)
            values.put(DatabaseContract.FilmColumn.OVERVIEW, overview)
            values.put(DatabaseContract.FilmColumn.POPULARITY, popularity)
            values.put(DatabaseContract.FilmColumn.DATE, date)
            values.put(DatabaseContract.FilmColumn.STATUS, status)
            values.put(DatabaseContract.FilmColumn.VOTE_AVERAGE, voteAverage)

            filmHelper.insert(values)

            Toast.makeText(this, getString(R.string.addedFav), Toast.LENGTH_SHORT).show()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        filmHelper.close()
    }
}
