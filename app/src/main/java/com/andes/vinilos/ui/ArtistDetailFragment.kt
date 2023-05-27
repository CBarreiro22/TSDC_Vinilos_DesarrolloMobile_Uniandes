package com.andes.vinilos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.ui.ArtistDetailFragment
import com.andes.vinilos.databinding.FragmentArtistDetailBinding
import com.andes.vinilos.models.Musician
import com.andes.vinilos.viewmodels.ArtistDetailViewModel
import com.bumptech.glide.Glide


class ArtistDetailFragment : Fragment() {
    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ArtistDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireActivity()
        viewModel =
            ViewModelProvider(this, ArtistDetailViewModel.Factory(activity.application)).get(
                ArtistDetailViewModel::class.java
            )
        val bundle = arguments
        if (bundle == null) {
            Log.d("Confirmation", "ArtistDetailFragment didn't receive info")
        } else {
            val arguments = ArtistDetailFragmentArgs.fromBundle(bundle)
            Log.d("********", "args " + arguments.name)
            val currentArtist = arguments.artistId?.let {
                Musician(
                    it.toInt(),
                    arguments.name.toString(),
                    arguments.image.toString(),
                    arguments.description.toString(),
                    arguments.birthday.toString()
                )
            }
            currentArtist?.let {
                viewModel.refreshData(it)
            }
            viewModel.artist.observe(viewLifecycleOwner) { artist ->
                binding.artist = artist
                Glide.with(requireContext()).load(artist.image).into(binding.imageDetailArtist)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}