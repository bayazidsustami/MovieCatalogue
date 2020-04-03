package com.the_b.moviecatalogue.adapter.favorite

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiRepository
import com.the_b.moviecatalogue.model.local.Films
import kotlinx.android.synthetic.main.list_item.view.*

class FavoriteFilmAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteFilmAdapter.ViewHolder>() {

    var listFilm = ArrayList<Films>()
        set(listFilm) {
            if (listFilm.size > 0){
                this.listFilm.clear()
            }
            this.listFilm.addAll(listFilm)

            notifyDataSetChanged()
        }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listFilm.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFilm[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(filmsItem: Films){
            with(itemView){
                Glide.with(context).load(ApiRepository.IMAGE_URL+filmsItem.photo).into(itemView.imgFilm)
                itemView.titleFilm.text = filmsItem.title

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(filmsItem)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Films)
    }

    fun addItem(films: Films){
        this.listFilm.add(films)
        notifyItemInserted(this.listFilm.size - 1)
    }

    fun removeItem(position: Int){
        this.listFilm.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFilm.size)
    }
}