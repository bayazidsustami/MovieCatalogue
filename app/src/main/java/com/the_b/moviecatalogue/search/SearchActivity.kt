package com.the_b.moviecatalogue.search

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.SectionPagerAdapter
import com.the_b.moviecatalogue.adapter.SectionSearchPagerAdapter
import kotlinx.android.synthetic.main.activity_search.*
import java.lang.Exception

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val sectionPagerAdapter = SectionSearchPagerAdapter(
            this,
            supportFragmentManager
        )
        viewPager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_action, menu)

        val searchView: SearchView = menu.findItem(R.id.searchView).actionView as SearchView
        searchView.queryHint = getString(R.string.search_label)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                try {
                    val bundle = Bundle()
                    val fragment = SearchFragment()
                    bundle.putString(SearchFragment.QUERY, query)
                    fragment.arguments = bundle
                    Log.d(com.the_b.moviecatalogue.TAG, bundle.toString())
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
}
