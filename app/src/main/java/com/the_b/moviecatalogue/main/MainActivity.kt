package com.the_b.moviecatalogue.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.SectionPagerAdapter
import com.the_b.moviecatalogue.favorites.FavoriteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0f

        val sectionPagerAdapter =
            SectionPagerAdapter(
                this,
                supportFragmentManager
            )
        viewPager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.change -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.gotoFav -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
