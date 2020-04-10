package com.the_b.moviecatalogue.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.TAG
import com.the_b.moviecatalogue.adapter.FilmAdapter
import com.the_b.moviecatalogue.adapter.TvShowAdapter
import com.the_b.moviecatalogue.main.MainViewModel
import com.the_b.moviecatalogue.model.FilmModel
import com.the_b.moviecatalogue.model.TvShowModel
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private lateinit var filmAdapter: FilmAdapter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var viewModel: MainViewModel

    private var dataFilm = mutableListOf<FilmModel>()
    private var dataTv = mutableListOf<TvShowModel>()

    private var queries = ""

    companion object{
        const val INDEX = "index"
        const val QUERY = "query"

        fun newInstance(index: Int): SearchFragment {
            val fragment = SearchFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, index)
            fragment.arguments = bundle

            return fragment
        }

        fun searchInstance(queries: String): SearchFragment {
            val fm = SearchFragment()
            val data = Bundle()
            data.putString(QUERY, queries)
            fm.arguments = data
            Log.d(TAG, data.getString(QUERY).toString())
            Log.d("ARGUMENT", fm.arguments.toString())

            return fm
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listSearch.setHasFixedSize(true)

        filmAdapter = FilmAdapter(dataFilm)
        tvShowAdapter = TvShowAdapter(dataTv)

        listSearch.adapter = filmAdapter
        listSearch.layoutManager = GridLayoutManager(context, 2)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        viewModel.getSearchFilm().observe(viewLifecycleOwner, Observer {
            if (it != null){
                filmAdapter.setData(it.results)
                showLoading(false)
            }
        })

        viewModel.getSearchTv().observe(viewLifecycleOwner, Observer {
            if (it != null){
                tvShowAdapter.setData(it.results)
                showLoading(false)
            }
        })



        var index = 0
        if (arguments != null){
            index = arguments?.getInt(INDEX, 0) as Int
            queries = arguments?.getString(QUERY, "") as String
            Log.d("ARGUMENTGET", queries)
            if (index == 1){
                loadDataTv(queries)
                listSearch.adapter = tvShowAdapter
            }
            loadDataFilm(queries)
        }
    }

    private fun loadDataFilm(query: String){
        viewModel.setSearchFilm(query)
        showLoading(true)
    }

    private fun loadDataTv(query: String) {
        viewModel.setSearchTv(query)
        showLoading(true)
    }

    private fun showLoading(state: Boolean){
        if (progressBar != null){
            if (state){
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }
}