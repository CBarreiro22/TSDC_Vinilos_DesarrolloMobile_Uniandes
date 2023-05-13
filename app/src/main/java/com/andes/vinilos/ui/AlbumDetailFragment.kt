package com.andes.vinilos.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentAlbumDetailBinding
import com.andes.vinilos.models.NewAlbum
import com.andes.vinilos.ui.adapters.AlbumDetailAdapter
import com.andes.vinilos.viewmodels.AlbumDetailViewModel
import com.bumptech.glide.Glide

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailViewModel
    private var viewModelAdapter: AlbumDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Album Detail"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(requireActivity())
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application))
            .get(AlbumDetailViewModel::class.java)
        val bundle = arguments
        if (bundle == null){
            Log.d("Confimation", "AlbumDetailFragment didnt receive info")
        } else {
            val arguments = AlbumDetailFragmentArgs.fromBundle(bundle)
            val currentAlbum = arguments.albumId?.let {
                NewAlbum(arguments.name.toString(),arguments.cover.toString(),
                    arguments.releaseDate.toString(),arguments.description.toString(),
                    arguments.genre.toString(), arguments.recordLabel.toString(), it.toInt())
            }
            if (currentAlbum != null) {
                viewModel.refreshData(currentAlbum)
            }
        }
        viewModel.album.observe(viewLifecycleOwner) {
            it.apply {
                binding.album=this
                Glide.with(requireContext()).load(this.cover).into(binding.imageDetailAlbum)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}