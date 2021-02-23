package com.the_b.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.the_b.moviecatalogue.databinding.ItemLoadViewFooterBinding

class LoadStateViewHolder(
    private val binds: ItemLoadViewFooterBinding,
    retry: () -> Unit
): RecyclerView.ViewHolder(binds.root) {
    init {
        binds.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState){
        if (loadState is LoadState.Error){
            binds.errorMsg.text = loadState.error.localizedMessage
        }
        binds.progressBar.isVisible = loadState is LoadState.Loading
        binds.retryButton.isVisible = loadState !is LoadState.Loading
        binds.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object{
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder{
            val view = ItemLoadViewFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadStateViewHolder(view, retry)
        }
    }
}