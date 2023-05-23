package com.andes.vinilos.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.andes.vinilos.databinding.FragmentAlbumDetailBinding
import com.andes.vinilos.models.Album
import com.andes.vinilos.ui.adapters.AlbumDetailAdapter
import com.andes.vinilos.viewmodels.AlbumDetailViewModel
import com.bumptech.glide.Glide

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.apply {
            title = "Album Detail"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireActivity()
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application))
            .get(AlbumDetailViewModel::class.java)
        val bundle = arguments
        if (bundle == null) {
            Log.d("Confirmation", "AlbumDetailFragment didn't receive info")
        } else {
            val arguments = AlbumDetailFragmentArgs.fromBundle(bundle)
            val currentAlbum = arguments.albumId?.let {
                Album(
                    it.toInt(),
                    arguments.name.toString(),
                    arguments.cover.toString(),
                    arguments.releaseDate.toString(),
                    arguments.description.toString(),
                    arguments.genre.toString(),
                    arguments.recordLabel.toString()
                )
            }
            currentAlbum?.let {
                viewModel.refreshData(it)
            }
        }
        viewModel.album.observe(viewLifecycleOwner) { album ->
            binding.album = album
            Glide.with(requireContext()).load(album.cover).into(binding.imageDetailAlbum)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}