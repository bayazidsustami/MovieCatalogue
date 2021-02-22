package com.the_b.moviecatalogue.search

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
import androidx.recyclerview.widget.GridLayoutManager
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.FilmAdapter
import com.the_b.moviecatalogue.adapter.TvShowAdapter
import com.the_b.moviecatalogue.details.DescActivity
import com.the_b.moviecatalogue.details.DescTvActivity
import com.the_b.moviecatalogue.main.MainViewModel
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.data.model.TvShowModel
import kotlinx.android.synthetic.main.activity_search.*

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
                        viewModel.getSearchFilm().observe(this@SearchActivity, Observer {
                            if (it != null){
                                filmAdapter.setData(it.results)
                                if (it.results.isEmpty()){
                                    Toast.makeText(applicationContext, "Not Found Films", Toast.LENGTH_SHORT).show()
                                    showLoading(false)
                                } else {
                                    showLoading(false)
                                }
                            }
                        })
                        Log.d("SPINERR ITEM", "FILM SEARCH")

                        listSearch.apply {
                            adapter = filmAdapter
                        }

                        filmAdapter.setOnItemClickCallback(object : FilmAdapter.OnItemClickCallback{
                            override fun onItemClick(data: FilmModel) {
                                val intent = Intent(this@SearchActivity, DescActivity::class.java)
                                intent.putExtra(DescActivity.EXTRA_DATA, data)
                                startActivity(intent)
                            }
                        })
                    }

                    resources.getString(R.string.tab_2) -> {
                        viewModel.getSearchTv().observe(this@SearchActivity, Observer {
                            if (it != null){
                                tvShowAdapter.setData(it.results)
                                if (it.results.isEmpty()){
                                    Toast.makeText(applicationContext, "Not Found Tv Show", Toast.LENGTH_SHORT).show()
                                    showLoading(false)
                                } else {
                                    showLoading(false)
                                }
                            }
                        })
                        Log.d("SPINERR ITEM", "TV SEARCH")

                        listSearch.apply {
                            adapter = tvShowAdapter
                        }

                        tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback{
                            override fun onItemClick(data: TvShowModel) {
                                val intent = Intent(this@SearchActivity, DescTvActivity::class.java)
                                intent.putExtra(DescTvActivity.EXTRA_DATA, data)
                                startActivity(intent)
                            }

                        })
                    }
                    else -> loadDataFilm(queries)
                }
            }
        }
    }

    private fun loadDataFilm(queries: String){
        showLoading(true)
        viewModel.setSearchFilm(queries)
    }

    private fun loadDataTv(queries: String){
        showLoading(true)
        viewModel.setSearchTv(queries)
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
