package com.the_b.moviecatalogue.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.the_b.moviecatalogue.adapter.DiscoverFilmsPagingAdapter
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.api.ApiService
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverPagingRepository
import com.the_b.moviecatalogue.databinding.FragmentHomeBinding
import com.the_b.moviecatalogue.ui.baseView.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var viewModel: MainViewModel
    private val pagingAdapter = DiscoverFilmsPagingAdapter()
    private var pagingJob: Job? = null

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

    private fun collectData(){
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading}
                .collect { list_film.scrollToPosition(0) }
        }
    }

    override fun setupView(binds: FragmentHomeBinding?) {
        with(binds?.listFilm!!){
            setHasFixedSize(true)
            adapter = pagingAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        val factory = MainVMFactory(
            DiscoverPagingRepository(ApiBuilder.createService(ApiService::class.java))
        )
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        loadFilm()
        collectData()
    }
}
