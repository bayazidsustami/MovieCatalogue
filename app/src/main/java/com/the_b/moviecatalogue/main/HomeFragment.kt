package com.the_b.moviecatalogue.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.FilmAdapter
import com.the_b.moviecatalogue.adapter.TvShowAdapter
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.model.TvShowModel
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverRepository
import com.the_b.moviecatalogue.details.DescActivity
import com.the_b.moviecatalogue.details.DescTvActivity
import com.the_b.moviecatalogue.utilities.Status
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var filmAdapter: FilmAdapter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var viewModel: MainViewModel

    private var dataFilm = mutableListOf<FilmModel>()
    private var dataTv = mutableListOf<TvShowModel>()

    companion object{
        const val INDEX = "index"

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        list_film.setHasFixedSize(true)

        tvShowAdapter = TvShowAdapter(dataTv)
        filmAdapter = FilmAdapter(dataFilm)

        list_film.adapter = filmAdapter
        list_film.layoutManager = GridLayoutManager(context, 2)


        val factory = MainVMFactory(DiscoverRepository.instance)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.getFilms().observe(viewLifecycleOwner, {
            if (it != null){
                when(it.status){
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Log.d("FILMS", it.message!!)
                    }
                    Status.SUCCESS -> {
                        showLoading(false)
                        it.data?.results?.let { data -> filmAdapter.setData(data) }
                    }
                }
            }
        })

        viewModel.getTvShow().observe(viewLifecycleOwner, {
            if (it != null){
                list_film.adapter = tvShowAdapter
                when(it.status){
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Log.d("FILMS", it.message!!)
                    }
                    Status.SUCCESS -> {
                        showLoading(false)
                        it.data?.results?.let { data -> tvShowAdapter.setData(data) }
                    }
                }
            }
        })

        val index: Int
        if (arguments != null){
            index = arguments?.getInt(INDEX, 0) as Int
            if (index == 1){
                loadDataTv()
                list_film.adapter = tvShowAdapter

                tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback{
                    override fun onItemClick(data: TvShowModel) {
                        val intent1 = Intent(context, DescTvActivity::class.java )
                        intent1.putExtra(DescTvActivity.EXTRA_DATA, data)
                        startActivity(intent1)
                    }
                })
            }
            Log.d("index", "index $index")

            loadDataFilm()
            filmAdapter.setOnItemClickCallback(object : FilmAdapter.OnItemClickCallback{
                override fun onItemClick(data: FilmModel) {
                    val intent = Intent(context, DescActivity::class.java)
                    intent.putExtra(DescActivity.EXTRA_DATA, data)
                    startActivity(intent)
                }
            })
        }
    }

    private fun loadDataFilm(){
        viewModel.setFilm()
    }

    private fun loadDataTv(){
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
