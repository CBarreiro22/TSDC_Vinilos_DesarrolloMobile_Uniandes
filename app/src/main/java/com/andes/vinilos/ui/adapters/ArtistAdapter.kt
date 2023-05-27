package com.andes.vinilos.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andes.vinilos.R
import com.andes.vinilos.databinding.MusicianItemBinding
import com.andes.vinilos.models.Musician
import com.andes.vinilos.ui.AlbumsFragmentDirections
import com.andes.vinilos.ui.ArtistFragment
import com.andes.vinilos.ui.ArtistFragmentDirections
import com.andes.vinilos.ui.SaveAlbumFragmentDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {
    // ViewHolder for each Artist
    class ArtistViewHolder(val viewDataBinding: MusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        // layout resource ID for the album item view
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_item
        }

        // bind the album data to the album item view using Glide for image loading
        fun bind(artist: Musician) {
            Glide.with(itemView)
                .load(artist.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image)
                )
                .into(viewDataBinding.image)
        }
    }

    // list of albums to display
    var artists: List<Musician> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // create a new AlbumViewHolder instance for each album item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: MusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false
        )
        return ArtistViewHolder(withDataBinding)
    }

    // return the number of artist in the list
    override fun getItemCount(): Int {
        return artists.size
    }

    // bind the artist data to the artist item view
    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        // set the artist data to the album item view using data binding
        holder.viewDataBinding.also {
            it.artist = artists[position]
        }
        // bind the album data to the album item view
        holder.bind(artists[position])
        // set a click listener for the album item view
        holder.viewDataBinding.root.setOnClickListener {
            val action = ArtistFragmentDirections.actionNavigationArtistToArtistDetail()
            val artist = artists[position]
            Log.d("******Artist Detail Id:", "***** "+ artist.image)
            action.artistId = artist.id.toString()
            action.name = artist.name
            action.description = artist.description
            action.image = artist.image
            action.birthday = artist.birthDate
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }
}