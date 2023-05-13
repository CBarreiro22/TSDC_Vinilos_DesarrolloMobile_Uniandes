package com.andes.vinilos.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentAlbumsBinding
import com.andes.vinilos.models.Album
import com.andes.vinilos.ui.adapters.AlbumsAdapter
import com.andes.vinilos.viewmodels.AlbumsViewModel

/**
This is a Fragment that displays a list of albums. It has a RecyclerView and uses an
AlbumsAdapter to populate the list with data. The AlbumsViewModel is used to provide
the data to the adapter. The Fragment also has a FloatingActionButton that navigates to}
a SaveAlbumFragment when clicked.
 */

class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumsViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        // Create an instance of the AlbumsAdapter and assign it to the viewModelAdapter property
        viewModelAdapter = AlbumsAdapter()

        // Set up a click listener for the saveAlbumFloatingButton. When clicked, it will
        // navigate to the SaveAlbumFragment.
        binding.saveAlbumFloatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_albums_to_saveAlbumFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get a reference to the RecyclerView from the binding and assign it to the
        // recyclerView property.
        recyclerView = binding.albumsRv
        // Set up the layout manager and adapter for the RecyclerView.
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set the title of the ActionBar to "Albums".
        val activity = requireActivity()
        activity.actionBar?.title = "Albums"
        // Create an instance of the AlbumsViewModel using the Factory class and assign it
        // to the viewModel property.
        viewModel = ViewModelProvider(this, AlbumsViewModel.Factory(activity.application))[AlbumsViewModel::class.java]
        // Observe the LiveData in the AlbumsViewModel and update the adapter's data
        // when it changes.
        viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                viewModelAdapter!!.albums = this
            }
        })
        // Observe the eventNetworkError LiveData in the AlbumsViewModel and call the
        // onNetworkError() function when it is true.
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set the _binding property to null to avoid memory leaks.
        _binding = null
    }

    /**
    This function is called when a network error occurs. It shows a Toast message and
    sets the isNetworkErrorShown LiveData in the AlbumsViewModel to true to prevent
    showing the error message multiple times.
     */
    private fun onNetworkError() {
        if (viewModel.isNetworkErrorShown.value != true) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}