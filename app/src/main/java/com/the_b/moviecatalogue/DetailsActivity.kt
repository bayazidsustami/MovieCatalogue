package com.the_b.moviecatalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    private lateinit var imgPreview: ImageView
    private lateinit var imgFilm: ImageView
    private lateinit var title: TextView
    private lateinit var desc: TextView
    private lateinit var director: TextView
    private lateinit var years: TextView
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        imgPreview = findViewById(R.id.imgPreview)
        imgFilm = findViewById(R.id.imgFilm)
        title = findViewById(R.id.titleFilm)
        desc = findViewById(R.id.description)
        director = findViewById(R.id.director_name)
        years = findViewById(R.id.years)
        back = findViewById(R.id.back)

        back.setOnClickListener { finish() }

        val films = intent.getParcelableExtra("EXTRA_DATA") as FilmModel

        title.text = films.title
        desc.text = films.desc
        director.text = films.director
        years.text = films.years

        Glide.with(this).load(films.imgFilm).into(imgPreview)
        Glide.with(this).load(films.imgFilm).into(imgFilm)
    }
}
