package com.the_b.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.data.model.FilmModel
import com.the_b.moviecatalogue.databinding.ListItemBinding

class DiscoverFilmsPagingAdapter: PagingDataAdapter<FilmModel, DiscoverFilmsPagingAdapter.FilmViewHolder>(
    DataComparator
) {
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null){
            holder.bind(items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(view)
    }

    class FilmViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(film: FilmModel){
            binding.titleFilm.text = film.title
            Glide.with(binding.root)
                .load(ApiBuilder.IMAGE_URL+film.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.imgFilm)
        }
    }

    object DataComparator: DiffUtil.ItemCallback<FilmModel>(){
        override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
            return oldItem == newItem
        }

    }
}