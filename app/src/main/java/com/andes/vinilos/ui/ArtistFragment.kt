package com.andes.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentArtistBinding
import com.andes.vinilos.ui.adapters.ArtistAdapter
import com.andes.vinilos.viewmodels.AlbumsViewModel
import com.andes.vinilos.viewmodels.ArtistsViewModel

class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null
    private lateinit var viewModel: ArtistsViewModel
    private val binding get() = _binding!!
    private var viewModelAdapter: ArtistAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        viewModelAdapter = ArtistAdapter()
        binding.createArtist.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_artist_to_saveArtistFragment)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.artistRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(requireActivity())
        activity.actionBar?.title = "Albums"
        // Create an instance of the AlbumsViewModel using the Factory class and assign it
        // to the viewModel property.
        viewModel = ViewModelProvider(this, ArtistsViewModel.Factory(activity.application))
            .get(ArtistsViewModel::class.java)
        // Observe the LiveData in the AlbumsViewModel and update the adapter's data
        // when it changes.
        viewModel.artists.observe(viewLifecycleOwner) {
            viewModelAdapter?.artists = it
        }
        // Observe the eventNetworkError LiveData in the AlbumsViewModel and call the
        // onNetworkError() function when it is true.
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}