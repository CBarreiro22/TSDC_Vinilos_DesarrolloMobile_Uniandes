package com.andes.vinilos.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.andes.vinilos.viewmodels.CreateAlbumViewModel
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentCreateAlbumBinding
import com.andes.vinilos.databinding.FragmentOptionsBinding
import com.andes.vinilos.viewmodels.OptionsViewModel

class CreateAlbumFragment : Fragment() {

    private var _binding: FragmentCreateAlbumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val optionsViewModel =
            ViewModelProvider(this).get(OptionsViewModel::class.java)
        // Inflar el layout a este fragmento
        _binding = FragmentCreateAlbumBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}