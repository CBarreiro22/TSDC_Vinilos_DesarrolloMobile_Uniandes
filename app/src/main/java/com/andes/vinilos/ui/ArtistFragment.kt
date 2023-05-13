package com.andes.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.andes.vinilos.viewmodels.AlbumsViewModel
import com.andes.vinilos.viewmodels.ArtistsViewModel

class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistsViewModel
    private var viewModelAdapter: ArtistAdapter? = null

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
        binding.saveArtistFloatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_albums_to_saveAlbumFragment)
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get a reference to the RecyclerView from the binding and assign it to the
        // recyclerView property.
        recyclerView = binding?.artistRv ?: return
        // Set up the layout manager and adapter for the RecyclerView.
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set the title of the ActionBar to "Albums".
        val activity = requireNotNull(requireActivity())
        activity.actionBar?.title = "Albums"
        // Create an instance of the AlbumsViewModel using the Factory class and assign it
        // to the viewModel property.
        viewModel = ViewModelProvider(this, ArtistsViewModel.Factory(activity.application))
            .get(ArtistsViewModel::class.java)
        // Observe the LiveData in the AlbumsViewModel and update the adapter's data
        // when it changes.
        viewModel.albums.observe(viewLifecycleOwner) {
            viewModelAdapter?.artist = it
        }
        // Observe the eventNetworkError LiveData in the AlbumsViewModel and call the
        // onNetworkError() function when it is true.
        /*
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }*/
    }
}