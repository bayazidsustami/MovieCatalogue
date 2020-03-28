package com.the_b.moviecatalogue.details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.main.HomeFragment
import com.the_b.moviecatalogue.model.FilmModel

class DescActivity : AppCompatActivity() {

    private lateinit var imgFilm: ImageView
    private lateinit var title: TextView
    private lateinit var desc: TextView
    private lateinit var director: TextView
    private lateinit var years: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc)

        imgFilm = findViewById(R.id.imageFilm)
        title = findViewById(R.id.titleFilm)
        desc = findViewById(R.id.overview)
        director = findViewById(R.id.directorName)
        years = findViewById(R.id.years)

        val films = intent.getParcelableExtra(HomeFragment.EXTRA_DATA) as FilmModel

        val actionBar = supportActionBar
        actionBar!!.title = films.title
        actionBar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
