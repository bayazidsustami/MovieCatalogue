package com.the_b.favoritefilm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.overview_layout.*

class DetailsActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val films = intent.getParcelableExtra(EXTRA_DATA) as FilmModel

        supportActionBar?.title = films.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(this).load("https://image.tmdb.org/t/p/w92"+films.photo).into(imageFilm)

        titleFilm.text = films.title
        releaseFilm.text = films.date
        popularity.text = films.popularity
        status.text = films.status
        voteLabel.text = films.voteAverage
        overview.text = films.overview
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
