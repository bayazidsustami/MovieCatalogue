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
import com.the_b.moviecatalogue.favorites.details.DescFavTv
import com.the_b.moviecatalogue.model.local.TvShows
import kotlinx.android.synthetic.main.list_item.view.*

class FavoriteTvAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder>() {

    var listTv = ArrayList<TvShows>()
        set(listTv) {
            if (listTv.size > 0){
                this.listTv.clear()
            }
            this.listTv.addAll(listTv)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listTv.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTv[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(tvItem: TvShows){
            with(itemView){
                Glide.with(context).load(ApiBuilder.IMAGE_URL+tvItem.photo).into(itemView.imgFilm)
                itemView.titleFilm.text = tvItem.title

                itemView.setOnClickListener(CustomClickListener(adapterPosition, object : CustomClickListener.OnItemClickCallback{
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DescFavTv::class.java)
                        intent.putExtra(DescFavTv.EXTRA_POSITION, position)
                        intent.putExtra(DescFavTv.EXTRA_DATA, tvItem)
                        activity.startActivityForResult(intent, DescFavTv.REQUEST_DEL_TV)
                    }

                }))
            }
        }
    }

    fun removeItem(position: Int){
        this.listTv.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTv.size)
    }
}