package com.andes.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.databinding.FragmentArtistBinding
import com.andes.vinilos.viewmodels.ArtistsViewModel

class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val artistsViewModel =
            ViewModelProvider(this).get(ArtistsViewModel::class.java)

        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textArtist
        artistsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}