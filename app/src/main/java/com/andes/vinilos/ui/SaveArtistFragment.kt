package com.andes.vinilos.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentCreateAlbumBinding
import com.andes.vinilos.databinding.FragmentCreateArtistBinding
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.Musician
import com.andes.vinilos.viewmodels.SaveAlbumViewModel
import com.andes.vinilos.viewmodels.SaveArtistViewModel
import kotlinx.coroutines.launch

class SaveArtistFragment: Fragment() {
    private var _binding: FragmentCreateArtistBinding? = null
    private lateinit var saveArtistViewModel: SaveArtistViewModel
    private val binding get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        saveArtistViewModel = ViewModelProvider(this).get(SaveArtistViewModel::class.java)

        saveArtistViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun onNetworkError() {
        if(!saveArtistViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            saveArtistViewModel.onNetworkErrorShown()
        }
    }

    private fun saveArtist(artist: Musician) {
        lifecycleScope.launch {
            saveArtistViewModel.saveArtist(artist,
                onSuccess = {
                    val toast = Toast.makeText(requireContext(), "Se ha creado el album exitosamente", Toast.LENGTH_LONG)
                    toast.show()
                    findNavController().navigate(R.id.action_saveAlbumFragment_to_navigation_albums)
                },
                onError = {
                    val toast = Toast.makeText(requireContext(), "No se pudo guardar el album en estos momentos", Toast.LENGTH_LONG)
                    toast.show()
                }
            )
        }
    }
}