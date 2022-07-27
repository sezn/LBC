package com.szn.lbc.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.szn.lbc.R
import com.szn.lbc.model.Album
import com.szn.lbc.network.GlideApp
import com.szn.lbc.ui.main.AlbumsAdapter.AlbumViewHolder

/***
 * Adapter for Display List of Album
 */
class AlbumsAdapter(val onItemSelected: OnItemSelectedListener): PagingDataAdapter<Album, AlbumViewHolder>(AlbumDiffer) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_album, viewGroup, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.titleTv.text = album?.title

        GlideApp.with(holder.itemView)
            .load(album?.url)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            onItemSelected.onClick(position, album!!)
        }
    }

    inner class AlbumViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.title_tv)
        val img: ImageView = itemView.findViewById(R.id.img_iv)
    }

    object AlbumDiffer : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnItemSelectedListener {
    fun onClick(position: Int, album: Album)
}
