package com.the_b.moviecatalogue

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item.view.*

class FilmAdapter(private val listFilm: ArrayList<FilmModel>): RecyclerView.Adapter<FilmAdapter.ViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(itemFilm: FilmModel){
            with(itemView){
                Glide.with(itemView.context)
                    .load(itemFilm.imgFilm)
                    .apply(RequestOptions().override(350,350))
                    .into(itemView.imgFilm)

                itemView.titleFilm.text = itemFilm.title

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClick(itemFilm)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listFilm.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listFilm[position])
    }

    interface OnItemClickCallback {
        fun onItemClick(data: FilmModel)
    }

}