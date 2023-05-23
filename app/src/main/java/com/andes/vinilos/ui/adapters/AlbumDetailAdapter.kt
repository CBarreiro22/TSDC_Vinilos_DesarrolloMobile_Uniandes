package com.andes.vinilos.ui.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andes.vinilos.R
import com.andes.vinilos.databinding.AlbumItemBinding
import com.andes.vinilos.models.Album
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class AlbumDetailAdapter : RecyclerView.Adapter<AlbumDetailAdapter.AlbumDetailViewHolder>() {

    private var album: Album? = null

    fun setAlbum(album: Album?) {
        this.album = album
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewDataBinding = AlbumItemBinding.inflate(inflater, parent, false)
        return AlbumDetailViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumDetailViewHolder, position: Int) {
        album?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return if (album != null) 1 else 0
    }

    inner class AlbumDetailViewHolder(private val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        // Bind the album data to the album item view using Glide for image loading
        fun bind(album: Album) {
            val context = viewDataBinding.root.context
            val coverUri = album.cover.toUri().buildUpon().scheme("https").build()
            Glide.with(context)
                .load(coverUri)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image)
                )
                .into(viewDataBinding.cover)
        }
    }
}