package com.andes.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andes.vinilos.R
import com.andes.vinilos.databinding.AlbumItemBinding
import com.andes.vinilos.databinding.ArtistItemBinding
import com.andes.vinilos.models.Musician

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    var artists :List<Musician> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.artist_item,
            parent,
            false
        )
        return ArtistViewHolder(withDataBinding.root)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ...
    }
}