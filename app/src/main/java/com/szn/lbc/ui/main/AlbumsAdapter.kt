package com.szn.lbc.ui.main

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.szn.lbc.R
import com.szn.lbc.model.Album
import com.szn.lbc.ui.main.AlbumsAdapter.AlbumViewHolder


/***
 * A Simple Adapter for Display List of Album
 */
class AlbumsAdapter(private val albums: List<Album>): RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_album, viewGroup, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.titleTv.text = album.title
        val userAgent = System.getProperty("http.agent")
        val url = GlideUrl(album.getImage(), LazyHeaders.Builder()
                .addHeader("User-Agent", userAgent)
                .build())

        Glide.with(holder.itemView)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    Log.e(TAG, "onLoadFailed ${e.toString()}")
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                    holder.img.setImageDrawable(resource)
                    return false
                }
            } )//.submit()
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.title_tv)
        val img: ImageView = itemView.findViewById(R.id.img_iv)
    }

    companion object {
        val TAG = AlbumsAdapter::class.java.simpleName
    }

}

