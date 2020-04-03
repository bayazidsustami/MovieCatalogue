package com.the_b.moviecatalogue.details

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.db.DatabaseContract
import com.the_b.moviecatalogue.db.TvShowHelper
import com.the_b.moviecatalogue.getLocale
import com.the_b.moviecatalogue.main.HomeFragment
import com.the_b.moviecatalogue.model.DescTvModel
import com.the_b.moviecatalogue.model.TvShowModel
import com.the_b.moviecatalogue.model.local.TvShows
import kotlinx.android.synthetic.main.activity_desc_tv.*
import kotlinx.android.synthetic.main.overview_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescTvActivity : AppCompatActivity() {

    private lateinit var viewModel: DescViewModel
    private lateinit var call: Call<DescTvModel>

    private lateinit var tvHelper: TvShowHelper
    private lateinit var details: DescTvModel
    private var tvShow: TvShows? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_tv)

        tvHelper = TvShowHelper.getInstance(applicationContext)
        tvHelper.open()

        val tv = intent.getParcelableExtra(HomeFragment.EXTRA_DATA) as TvShowModel

        val actionBar = supportActionBar
        actionBar!!.title = tv.name
        actionBar.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DescViewModel::class.java)
        viewModel.getDetailsTv().observe(this, Observer {
            if (it != null){
                showLoading(false)
                showTv(it)
            }
        })

        val apiService = ApiRepository.createService(ApiService::class.java)
        call = apiService.loadDetailTv(tv.id.toString(), getLocale())
        showLoading(true)

        if (call.isExecuted){
            return
        }

        call.enqueue(object : Callback<DescTvModel>{
            override fun onFailure(call: Call<DescTvModel>, t: Throwable) {
                showLoading(false)
                Log.d(com.the_b.moviecatalogue.TAG, "error ---> ${t.message}")
            }

            override fun onResponse(call: Call<DescTvModel>, response: Response<DescTvModel>) {
                showLoading(false)

                val data = response.body() as DescTvModel
                viewModel.setDetailsTv(data)

                details = DescTvModel(
                    data.id,
                    data.title,
                    data.imageTv,
                    data.overview,
                    data.numEpisode,
                    data.voteAverage,
                    data.status,
                    data.date
                )
            }
        })

        btn_addFav.setOnClickListener {
            val id = details.id!!
            val title = details.title
            val photo = details.imageTv
            val overview = details.overview
            val numEpisodes = details.numEpisode
            val date = details.date
            val status = details.status
            val voteAverage = details.voteAverage

            tvShow?.id = id
            tvShow?.title = title
            tvShow?.photo = photo
            tvShow?.overview = overview
            tvShow?.episodes = numEpisodes
            tvShow?.date = date
            tvShow?.status = status
            tvShow?.voteAverage = voteAverage

            val values = ContentValues()
            values.put(DatabaseContract.TvShowColumn._ID, id)
            values.put(DatabaseContract.TvShowColumn.TITLE, title)
            values.put(DatabaseContract.TvShowColumn.PHOTO, photo)
            values.put(DatabaseContract.TvShowColumn.OVERVIEW, overview)
            values.put(DatabaseContract.TvShowColumn.EPISODES, numEpisodes)
            values.put(DatabaseContract.TvShowColumn.DATE, date)
            values.put(DatabaseContract.TvShowColumn.STATUS, status)
            values.put(DatabaseContract.TvShowColumn.VOTE_AVERAGE, voteAverage)

            tvHelper.insert(values)

        }

    }

    private fun showTv(tv: DescTvModel){
        titleFilm.text = tv.title
        overview.text = tv.overview
        popularity.text = tv.numEpisode
        status.text = tv.status
        voteLabel.text = tv.voteAverage
        releaseFilm.text = tv.date
        Glide.with(this).load(ApiRepository.IMAGE_URL+tv.imageTv).into(imageFilm)
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
        tvHelper.close()
    }
}
