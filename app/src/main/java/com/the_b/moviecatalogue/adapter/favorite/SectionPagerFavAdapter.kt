package com.the_b.moviecatalogue.adapter.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.favorites.FavoriteFragment

class SectionPagerFavAdapter(private val context: Context, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLE = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getItem(position: Int): Fragment {
        return FavoriteFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLE[position])
    }

    override fun getCount(): Int = 2
}