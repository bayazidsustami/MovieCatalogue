package com.the_b.moviecatalogue.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.FilmAdapter
import com.the_b.moviecatalogue.adapter.TvShowAdapter
import com.the_b.moviecatalogue.details.DescActivity
import com.the_b.moviecatalogue.details.DescTvActivity
import com.the_b.moviecatalogue.model.FilmModel
import com.the_b.moviecatalogue.model.TvShowModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var filmAdapter: FilmAdapter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var viewModel: MainViewModel

    private var dataFilm = mutableListOf<FilmModel>()
    private var dataTv = mutableListOf<TvShowModel>()

    companion object{
        const val INDEX = "index"
        const val EXTRA_DATA = "extra data"

        fun newInstance(index: Int): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, index)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list_film.setHasFixedSize(true)

        tvShowAdapter = TvShowAdapter(dataTv)
        filmAdapter = FilmAdapter(dataFilm)

        list_film.adapter = filmAdapter
        list_film.layoutManager = GridLayoutManager(context, 2)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        viewModel.getFilms().observe(viewLifecycleOwner, Observer {
            if (it != null){
                filmAdapter.setData(it.results)
                showLoading(false)
            }
        })

        viewModel.getTvShow().observe(viewLifecycleOwner, Observer {
            if (it != null){
                list_film.adapter = tvShowAdapter
                tvShowAdapter.setData(it.results)
                showLoading(false)
            }
        })

        var index = 0
        if (arguments != null){
            index = arguments?.getInt(INDEX, 0) as Int
            if (index == 1){
                loadDataTv()
                list_film.adapter = tvShowAdapter

                tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback{
                    override fun onItemClick(data: TvShowModel) {
                        val intent1 = Intent(context, DescTvActivity::class.java )
                        intent1.putExtra(EXTRA_DATA, data)
                        startActivity(intent1)
                    }
                })
            }
            Log.d("index", "index $index")

            loadDataFilm()
            filmAdapter.setOnItemClickCallback(object : FilmAdapter.OnItemClickCallback{
                override fun onItemClick(data: FilmModel) {
                    val intent = Intent(context, DescActivity::class.java)
                    intent.putExtra(EXTRA_DATA, data)
                    startActivity(intent)
                }
            })
        }
    }

    private fun loadDataFilm(){
        showLoading(true)
        viewModel.setFilm()
    }

    private fun loadDataTv(){
        showLoading(true)
        viewModel.setTvShow()
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
