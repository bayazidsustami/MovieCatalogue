package com.the_b.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.model.FilmModel
import kotlinx.android.synthetic.main.list_item.view.*

class FilmAdapter(private val listFilm: MutableList<FilmModel>): RecyclerView.Adapter<FilmAdapter.ViewHolder>(){

    fun setData(movies: List<FilmModel>){
        listFilm.clear()
        listFilm.addAll(movies)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(itemFilm: FilmModel){
            with(itemView){
                Glide.with(context).load(ApiRepository.IMAGE_URL+itemFilm.poster_path).into(itemView.imgFilm)
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