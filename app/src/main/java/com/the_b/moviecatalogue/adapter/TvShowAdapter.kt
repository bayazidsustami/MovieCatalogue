package com.the_b.moviecatalogue.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.TAG
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.model.TvShowModel
import kotlinx.android.synthetic.main.list_item.view.*

class TvShowAdapter (private val listTv: MutableList<TvShowModel>): RecyclerView.Adapter<TvShowAdapter.ViewHolder>(){

    fun setData(tvShow: List<TvShowModel>){
        listTv.clear()
        listTv.addAll(tvShow)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        fun bind(itemTv: TvShowModel){
            with(itemView){
                Glide.with(context).load(ApiRepository.IMAGE_URL+itemTv.poster_path).into(itemView.imgFilm)
                itemView.titleFilm.text = itemTv.name

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClick(itemTv)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listTv.size

    override fun onBindViewHolder(holder: TvShowAdapter.ViewHolder, position: Int) {
        holder.bind(listTv[position])
    }

    interface OnItemClickCallback {
        fun onItemClick(data: TvShowModel)
    }
}