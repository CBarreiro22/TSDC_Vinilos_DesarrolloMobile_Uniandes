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

class AlbumDetailAdapter : RecyclerView.Adapter<AlbumDetailAdapter.AlbumDetailViewHolder>(){

    class AlbumDetailViewHolder(val viewDataBinding: AlbumItemBinding) :
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

    var album: NewAlbum? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // create a new AlbumViewHolder instance for each album item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumDetailViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumDetailViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumDetailViewHolder, position: Int) {
    }

    // return the number of albums in the list
    override fun getItemCount(): Int {
        return 1
    }

}