package com.the_b.moviecatalogue.ui.features.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiBuilder.IMAGE_URL
import com.the_b.moviecatalogue.data.db.FilmHelper
import com.the_b.moviecatalogue.data.db.helper.MappingFilmHelper
import com.the_b.moviecatalogue.data.model.local.Films

internal class RemoteViewsFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory {
    private var filmItem = ArrayList<Films>()

    private lateinit var filmHelper: FilmHelper

    override fun onCreate() {
        filmHelper = FilmHelper.getInstance(context)
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        filmItem.clear()
        val binderIdentityToken = Binder.clearCallingIdentity()

        val cursor = filmHelper.queryAll()
        val result = MappingFilmHelper.mapCursorToArrayList(cursor)
        filmItem.addAll(result)

        Binder.restoreCallingIdentity(binderIdentityToken)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_widget)
        if (filmItem.size == 0){
            return rv
        }

        val film = filmItem[position]

        val imageView =Glide.with(context)
            .asBitmap()
            .load(IMAGE_URL+film.photo)
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.imageView, imageView)

        val extras = bundleOf(FavoriteWidget.EXTRA_ITEM to film.id)

        val fillIntent = Intent()
        fillIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)
        return rv
    }

    override fun getCount(): Int = filmItem.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}
}