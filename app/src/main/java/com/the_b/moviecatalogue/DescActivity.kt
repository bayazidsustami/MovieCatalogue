package com.the_b.moviecatalogue

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

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

        title.text = films.title
        desc.text = films.desc
        director.text = films.director
        years.text = films.years

        Glide.with(this).load(films.imgFilm).into(imgFilm)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
