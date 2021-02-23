package com.the_b.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.data.model.TvShowModel
import com.the_b.moviecatalogue.databinding.ListItemBinding

class DiscoverTvShowPagingAdapter: PagingDataAdapter<TvShowModel, DiscoverTvShowPagingAdapter.TvShowViewHolder>(DataComparator) {

    inner class TvShowViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tvShow: TvShowModel){
            binding.titleFilm.text = tvShow.name
            Glide.with(binding.root)
                .load(ApiBuilder.IMAGE_URL+tvShow.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.imgFilm)
        }
    }

    object DataComparator: DiffUtil.ItemCallback<TvShowModel>(){
        override fun areItemsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null){
            holder.bind(items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(view)
    }
}