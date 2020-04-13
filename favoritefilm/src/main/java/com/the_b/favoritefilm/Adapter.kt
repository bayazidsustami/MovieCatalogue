package com.the_b.favoritefilm

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*

class Adapter(private val listFilmModel: ArrayList<FilmModel>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFilmModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listFilmModel[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(filmItem: FilmModel){
            with(itemView){
                titleFilm.text = filmItem.title
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w92"+filmItem.photo).into(imgFilm)

                itemView.setOnClickListener {
                    val intent = Intent(context, DetailsActivity::class.java).apply {
                        putExtra(DetailsActivity.EXTRA_DATA, filmItem)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

}