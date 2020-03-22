package com.the_b.moviecatalogue

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var filmAdapter: FilmAdapter
    private lateinit var title: Array<String>
    private lateinit var desc: Array<String>
    private lateinit var director: Array<String>
    private lateinit var year: Array<String>
    private lateinit var imageFilm: TypedArray
    private var films = arrayListOf<FilmModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filmAdapter = FilmAdapter(this)
        list_film.adapter = filmAdapter

        setData()
        addItem()

        list_film.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val film = FilmModel(title[i], desc[i], director[i], year[i], imageFilm.getResourceId(i, -1))
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("EXTRA_DATA", film)
            startActivity(intent)
        }
    }

    private fun setData() {
        title = resources.getStringArray(R.array.title_film)
        desc = resources.getStringArray(R.array.desc_film)
        director = resources.getStringArray(R.array.director)
        year = resources.getStringArray(R.array.years)
        imageFilm = resources.obtainTypedArray(R.array.image_film)
    }

    private fun addItem() {
        for (i in title.indices) {
            val film = FilmModel(
                title[i],
                desc[i],
                director[i],
                year[i],
                imageFilm.getResourceId(i, -1)
            )
            films.add(film)
        }
        filmAdapter.filmItem = films
    }
}
