package com.the_b.moviecatalogue.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.adapter.favorite.SectionPagerFavAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.favorite)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionFavAdapter = SectionPagerFavAdapter(
            this, supportFragmentManager
        )
        viewPagerFav.adapter = sectionFavAdapter
        tabsFav.setupWithViewPager(viewPagerFav)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
