package com.the_b.moviecatalogue.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.FilmAdapter
import com.the_b.moviecatalogue.adapter.TvShowAdapter
import com.the_b.moviecatalogue.ui.details.DescActivity
import com.the_b.moviecatalogue.ui.details.DescTvActivity
import com.the_b.moviecatalogue.ui.main.MainViewModel
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.model.TvShowModel
import kotlinx.android.synthetic.main.activity_search.*

@ExperimentalPagingApi
class SearchActivity : AppCompatActivity() {

    companion object{
        const val QUERY = "query"
    }

    private lateinit var filmAdapter: FilmAdapter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var viewModel: MainViewModel

    private var dataFilm = mutableListOf<FilmModel>()
    private var dataTv = mutableListOf<TvShowModel>()

    private var queries = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        queries = intent.getStringExtra(QUERY) ?: ""

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val spinnerItem = arrayOf(resources.getString(R.string.tab_1), resources.getString(R.string.tab_2))
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        spinner.adapter = spinnerAdapter

        filmAdapter = FilmAdapter(dataFilm)
        tvShowAdapter = TvShowAdapter(dataTv)

        listSearch.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(spinner.selectedItem.toString()){

                    resources.getString(R.string.tab_1) -> {

                    }

                    resources.getString(R.string.tab_2) -> {

                    }
                    else -> loadDataFilm(queries)
                }
            }
        }
    }

    private fun loadDataFilm(queries: String){
        showLoading(true)

    }

    private fun loadDataTv(queries: String){
        showLoading(true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_action, menu)

        val searchView: SearchView = menu.findItem(R.id.searchView).actionView as SearchView
        searchView.apply {
            queryHint = getString(R.string.search_label)
            requestFocusFromTouch()
            requestFocus()
            isFocusable = true
            isIconified = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                try {
                    loadDataFilm(query)
                    loadDataTv(query)
                } catch (e: Exception){
                    Log.d("error bundle", e.message.toString())
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(state: Boolean){
        if (state){
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
