package com.the_b.moviecatalogue.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.DiscoverFilmsPagingAdapter
import com.the_b.moviecatalogue.adapter.FilmAdapter
import com.the_b.moviecatalogue.adapter.TvShowAdapter
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.model.TvShowModel
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverPagingRepository
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverRepository
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var filmAdapter: FilmAdapter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var viewModel: MainViewModel
    private val pagingAdapter = DiscoverFilmsPagingAdapter()
    private var pagingJob: Job? = null

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

    private fun loadFilm(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            viewModel.getListFilmPaging().collect {
                pagingAdapter.submitData(it)
            }
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

        list_film.adapter = pagingAdapter
        list_film.layoutManager = GridLayoutManager(context, 2)


        val factory = MainVMFactory(
            DiscoverRepository.instance,
            DiscoverPagingRepository(ApiBuilder.createService(ApiService::class.java)))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        loadFilm()
        collectData()

        /*viewModel.getFilms().observe(viewLifecycleOwner, {
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
        })*/

        /*viewModel.getTvShow().observe(viewLifecycleOwner, {
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
        })*/

        /*val index: Int
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

            //loadDataFilm()
            filmAdapter.setOnItemClickCallback(object : FilmAdapter.OnItemClickCallback{
                override fun onItemClick(data: FilmModel) {
                    val intent = Intent(context, DescActivity::class.java)
                    intent.putExtra(DescActivity.EXTRA_DATA, data)
                    startActivity(intent)
                }
            })
        }*/
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

    private fun collectData(){
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading}
                .collect { list_film.scrollToPosition(0) }
        }
    }
}
