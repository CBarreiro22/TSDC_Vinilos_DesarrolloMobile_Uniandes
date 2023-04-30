package com.andes.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.andes.vinilos.R
import com.andes.vinilos.databinding.AlbumItemBinding
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.NewAlbum
import com.bumptech.glide.Glide
import com.andes.vinilos.ui.AlbumsFragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {
    // ViewHolder for each album
    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        // layout resource ID for the album item view
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }

        // bind the album data to the album item view using Glide for image loading
        fun bind(album: NewAlbum) {
            Glide.with(itemView)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image)
                )
                .into(viewDataBinding.cover)
        }
    }

    // list of albums to display
    var albums: List<NewAlbum> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // create a new AlbumViewHolder instance for each album item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumViewHolder(withDataBinding)
    }

    // return the number of albums in the list
    override fun getItemCount(): Int {
        return albums.size
    }

    // bind the album data to the album item view
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        // set the album data to the album item view using data binding
        holder.viewDataBinding.also {
            it.album = albums[position]
        }
        // bind the album data to the album item view
        holder.bind(albums[position])
        // set a click listener for the album item view
        holder.viewDataBinding.root.setOnClickListener {

        }
    }
}