package com.the_b.moviecatalogue.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.SectionPagerAdapter
import com.the_b.moviecatalogue.ui.favorites.FavoriteActivity
import com.the_b.moviecatalogue.ui.search.SearchActivity
import com.the_b.moviecatalogue.settings.SettingActivity
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
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
            R.id.notificationSet -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
            R.id.search -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
