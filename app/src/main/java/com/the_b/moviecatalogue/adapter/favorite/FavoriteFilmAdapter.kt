package com.the_b.moviecatalogue.adapter.favorite

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the_b.moviecatalogue.R
import com.the_b.moviecatalogue.api.ApiBuilder
import com.the_b.moviecatalogue.favorites.details.DescFavFilm
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
                Glide.with(context).load(ApiBuilder.IMAGE_URL+filmsItem.photo).into(itemView.imgFilm)
                itemView.titleFilm.text = filmsItem.title

                itemView.setOnClickListener(CustomClickListener(adapterPosition, object : CustomClickListener.OnItemClickCallback{
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DescFavFilm::class.java)
                        intent.putExtra(DescFavFilm.EXTRA_DATA, filmsItem)
                        intent.putExtra(DescFavFilm.EXTRA_POSITION, position)
                        activity.startActivityForResult(intent, DescFavFilm.REQUEST_DEL)
                    }
                }))
            }
        }
    }

    fun removeItem(position: Int){
        this.listFilm.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFilm.size)
    }
}