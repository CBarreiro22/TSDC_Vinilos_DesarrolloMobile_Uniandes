package com.andes.vinilos.ui

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.ui.ArtistDetailFragment
import com.andes.vinilos.databinding.FragmentArtistDetailBinding
import com.andes.vinilos.models.Musician
import com.andes.vinilos.viewmodels.ArtistDetailViewModel
import com.andes.vinilos.viewmodels.DefaultCoroutineDispatcherProvider
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
        val dispatcherProvider = DefaultCoroutineDispatcherProvider()
        viewModel =
            ViewModelProvider(this, ArtistDetailViewModel.Factory(activity.application,dispatcherProvider)).get(
                ArtistDetailViewModel::class.java
            )
        val bundle = arguments
        if (bundle == null) {
            Log.d("Confirmation", "ArtistDetailFragment didn't receive info")
        } else {
            val arguments = ArtistDetailFragmentArgs.fromBundle(bundle)
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
        val premios = mutableListOf<String>()
        viewModel.prizes.observe(viewLifecycleOwner) { prizeList ->
            prizeList?.let {
                for (prize in it) {
                    premios.add(prize.name)
                }
                val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, premios)
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.premiosLista.adapter = adapter
            }
        }

        // Agregar un listener al Spinner
        binding.premiosLista.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val premioSeleccionado = premios[position]
                Log.d("Premio seleccionado", premioSeleccionado)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}