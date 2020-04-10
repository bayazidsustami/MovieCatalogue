package com.the_b.moviecatalogue.favorites

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.favorite.FavoriteFilmAdapter
import com.the_b.moviecatalogue.adapter.favorite.FavoriteTvAdapter
import com.the_b.moviecatalogue.db.DatabaseContract.FilmColumn.Companion.CONTENT_URI_FILM
import com.the_b.moviecatalogue.db.FilmHelper
import com.the_b.moviecatalogue.db.TvShowHelper
import com.the_b.moviecatalogue.favorites.details.DescFavFilm
import com.the_b.moviecatalogue.favorites.details.DescFavTv
import com.the_b.moviecatalogue.helper.MappingFilmHelper
import com.the_b.moviecatalogue.helper.MappingTvShowHelper
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var favFilmAdapter: FavoriteFilmAdapter
    private lateinit var filmHelper: FilmHelper
    private lateinit var favTvAdapter: FavoriteTvAdapter
    private lateinit var tvHelper: TvShowHelper

    companion object{
        const val INDEX = "index"

        fun newInstance(index: Int): FavoriteFragment {
            val f = FavoriteFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, index)
            f.arguments = bundle

            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list_film.layoutManager = GridLayoutManager(context, 2)
        list_film.setHasFixedSize(true)
        favFilmAdapter = FavoriteFilmAdapter(context as Activity)
        favTvAdapter = FavoriteTvAdapter(context as Activity)
        list_film.adapter = favFilmAdapter

        var index = 0
        if (arguments != null){
            index = arguments?.getInt(INDEX, 0) as Int
            tvHelper = TvShowHelper.getInstance(context as Activity)
            tvHelper.open()
            if (index == 1){
                list_film.adapter = favTvAdapter
                loadTvAsync()
            }
            filmHelper = FilmHelper.getInstance(context as Activity)
            filmHelper.open()
            loadFilmsAsync()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null){
            when(requestCode){
                DescFavFilm.REQUEST_DEL -> {
                    if (resultCode == DescFavFilm.RESULT_DEL){
                        val position = data.getIntExtra(DescFavFilm.EXTRA_POSITION, 0)
                        favFilmAdapter.removeItem(position)
                        Toast.makeText(context, "Success Delete", Toast.LENGTH_SHORT).show()
                    }
                }
                DescFavTv.REQUEST_DEL_TV -> {
                    if (resultCode == DescFavTv.RESULT_DEL_TV){
                        val position = data.getIntExtra(DescFavTv.EXTRA_POSITION, 0)
                        favTvAdapter.removeItem(position)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadFilmsAsync(){
        GlobalScope.launch(Dispatchers.Main){
            val defferedFilms = async(Dispatchers.IO){
                val cursor = filmHelper.queryAll()
                //val cursor = context?.contentResolver?.query(CONTENT_URI_FILM, null, null, null,null)
                MappingFilmHelper.mapCursorToArrayList(cursor)
            }
            val film = defferedFilms.await()
            if (film.size > 0){
                favFilmAdapter.listFilm = film
            } else {
                favFilmAdapter.listFilm = ArrayList()
                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTvAsync(){
        GlobalScope.launch(Dispatchers.Main){
            val defferedTv = async(Dispatchers.IO){
                val cursor = tvHelper.queryAll()
                MappingTvShowHelper.mapToCursorArrayList(cursor)
            }
            val tv = defferedTv.await()
            if (tv.size > 0){
                favTvAdapter.listTv = tv
            } else {
                favTvAdapter.listTv = ArrayList()
                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadFilmsAsync()
        loadTvAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        filmHelper.close()
        tvHelper.close()
    }
}
