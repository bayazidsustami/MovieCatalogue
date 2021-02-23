package com.the_b.moviecatalogue.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.the_b.moviecatalogue.adapter.DiscoverFilmsPagingAdapter
import com.the_b.moviecatalogue.adapter.DiscoverTvShowPagingAdapter
import com.the_b.moviecatalogue.adapter.LoadViewStateAdapter
import com.the_b.moviecatalogue.data.repositories.discover.DiscoverPagingRepository
import com.the_b.moviecatalogue.databinding.FragmentHomeBinding
import com.the_b.moviecatalogue.ui.baseView.BaseFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var viewModel: MainViewModel
    private val filmsPagingAdapter = DiscoverFilmsPagingAdapter()
    private val tvShowPagingAdapter = DiscoverTvShowPagingAdapter()

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
                filmsPagingAdapter.submitData(it)
            }
        }
    }

    private fun loadTvShow(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            viewModel.getListTvPaging().collect {
                tvShowPagingAdapter.submitData(it)
            }
        }
    }

    private fun collectDataFilm(binds: FragmentHomeBinding?){
        lifecycleScope.launch {
            filmsPagingAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading}
                .collect { binds?.listFilm?.scrollToPosition(0) }
        }
    }

    private fun collectDataTvShow(binds: FragmentHomeBinding?){
        lifecycleScope.launch {
            tvShowPagingAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binds?.listFilm?.scrollToPosition(0) }
        }
    }

    override fun setupView(binds: FragmentHomeBinding?) {

        with(binds?.listFilm!!){
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
        }

        val factory = MainVMFactory(
            DiscoverPagingRepository.instance
        )
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        if (arguments != null){
            val index = arguments?.getInt(INDEX)
            Log.d(INDEX, index.toString())
            if (index == 0){
                initAdapterFilm(binds)
            } else if(index == 1){
                initAdapterTv(binds)
            }
        }
    }

    private fun initAdapterFilm(binds: FragmentHomeBinding){
        binds.listFilm.adapter = filmsPagingAdapter.withLoadStateFooter(
            footer = LoadViewStateAdapter { filmsPagingAdapter.retry() }
        )
        loadFilm()
        collectDataFilm(binds)
        binds.retryButton.setOnClickListener { filmsPagingAdapter.retry() }
        filmsPagingAdapter.addLoadStateListener {loadState ->
            binds.listFilm.isVisible = loadState.source.refresh is LoadState.NotLoading
            binds.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binds.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?:loadState.source.prepend as? LoadState.Error
                ?:loadState.append as? LoadState.Error
                ?:loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(requireContext(), "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapterTv(binds: FragmentHomeBinding){
        binds.listFilm.adapter = tvShowPagingAdapter.withLoadStateFooter(
            footer = LoadViewStateAdapter{tvShowPagingAdapter.retry()}
        )
        loadTvShow()
        collectDataTvShow(binds)
        binds.retryButton.setOnClickListener { tvShowPagingAdapter.retry() }
        tvShowPagingAdapter.addLoadStateListener {loadState ->
            binds.listFilm.isVisible = loadState.source.refresh is LoadState.NotLoading
            binds.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binds.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?:loadState.source.prepend as? LoadState.Error
                ?:loadState.append as? LoadState.Error
                ?:loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(requireContext(), "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pagingJob?.cancel()
    }
}
