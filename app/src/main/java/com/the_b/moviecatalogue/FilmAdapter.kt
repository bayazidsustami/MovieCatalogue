package com.the_b.moviecatalogue

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide

class FilmAdapter internal constructor(private val context: Context): BaseAdapter(){

    internal var filmItem = arrayListOf<FilmModel>()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var itemView = view
        if (itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val film = getItem(position) as FilmModel
        viewHolder.bind(film)
        return itemView
    }

    override fun getItem(position: Int): Any = filmItem[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = filmItem.size

    private inner class ViewHolder internal constructor(view: View){

        private val title: TextView = view.findViewById(R.id.titleFilm)
        private val desc: TextView = view.findViewById(R.id.descFilm)
        private var imgFilm: ImageView = view.findViewById(R.id.imgFilm)

        internal fun bind(films: FilmModel){
            title.text = films.title
            desc.text = films.desc
            Glide.with(context).load(films.imgFilm).into(imgFilm)
        }
    }
}