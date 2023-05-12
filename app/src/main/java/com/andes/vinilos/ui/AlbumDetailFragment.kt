package com.andes.vinilos.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andes.vinilos.databinding.FragmentAlbumDetailBinding
import com.andes.vinilos.models.NewAlbum
import com.andes.vinilos.ui.adapters.AlbumDetailAdapter
import com.andes.vinilos.viewmodels.AlbumDetailViewModel

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumDetailViewModel
    private var viewModelAdapter: AlbumDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlbumDetailBinding.inflate(layoutInflater)
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        // Create an instance of the AlbumsAdapter and assign it to the viewModelAdapter property
        viewModelAdapter = AlbumDetailAdapter()

        val bundle = arguments
        if (bundle == null){
            Log.d("Confimation", "AlbumDetailFragment didnt receive info")
        } else {
            val arguments = AlbumDetailFragmentArgs.fromBundle(bundle)
            val currentAlbum = arguments.albumId?.let {
                NewAlbum(arguments.name.toString(),arguments.cover.toString(), arguments.releaseDate.toString(),arguments.description.toString(), arguments.genre.toString(), arguments.recordLabel.toString(), it.toInt())
            }
            if (currentAlbum != null) {
                viewModel.refreshData(currentAlbum)
            }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get a reference to the RecyclerView from the binding and assign it to the
        // recyclerView property.
//        recyclerView = binding?.albumsRv ?: return
        // Set up the layout manager and adapter for the RecyclerView.
        // Retrieve the bundle value for the "amount" key
        recyclerView = binding?.albumsDetailRv ?: return
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set the title of the ActionBar to "Albums".
        val activity = requireNotNull(requireActivity())
        activity.actionBar?.title = "Album Detail"

        // Create an instance of the AlbumsViewModel using the Factory class and assign it
        // to the viewModel property.
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application))
            .get(AlbumDetailViewModel::class.java)
        // Observe the LiveData in the AlbumsViewModel and update the adapter's data
        // when it changes.
        viewModel.album.observe(viewLifecycleOwner) {
            viewModelAdapter?.album = it
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
//        if (viewModel.isNetworkErrorShown.value != true) {
//            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
//            viewModel.onNetworkErrorShown()
//        }
    }

}