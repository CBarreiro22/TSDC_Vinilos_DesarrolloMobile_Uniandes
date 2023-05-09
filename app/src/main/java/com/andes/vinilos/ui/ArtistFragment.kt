package com.andes.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentAlbumsBinding
import com.andes.vinilos.databinding.FragmentArtistBinding
import com.andes.vinilos.ui.adapters.AlbumsAdapter
import com.andes.vinilos.ui.adapters.ArtistAdapter
import com.andes.vinilos.viewmodels.ArtistsViewModel

class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null
    private lateinit var viewModel: ArtistsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var viewModelAdapter: ArtistAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        // Create an instance of the AlbumsAdapter and assign it to the viewModelAdapter property
        viewModelAdapter = ArtistAdapter()

        // Set up a click listener for the saveAlbumFloatingButton. When clicked, it will
        // navigate to the SaveAlbumFragment.
        /*binding.saveAlbumFloatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_albums_to_saveAlbumFragment)
        }*/
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerViewArtist)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}