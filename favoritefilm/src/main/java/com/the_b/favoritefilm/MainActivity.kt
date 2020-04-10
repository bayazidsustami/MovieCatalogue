package com.the_b.favoritefilm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var filmAdapter: Adapter
    private lateinit var viewModel: MainViewModel

    private val filmList = ArrayList<FilmModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filmAdapter = Adapter(filmList)
        recylerView.setHasFixedSize(true)
        recylerView.adapter = filmAdapter
        recylerView.layoutManager = GridLayoutManager(this,2)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        viewModel.getData().observe(this, Observer {
            if (it != null){
                filmList.clear()
                filmList.addAll(it)
                filmAdapter.notifyDataSetChanged()
            }
        })

        viewModel.setData(this)
    }
}
