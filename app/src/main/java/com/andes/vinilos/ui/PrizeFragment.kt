package com.andes.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.databinding.FragmentCreatePrizeBinding
import com.andes.vinilos.viewmodels.OptionsViewModel

class PrizeFragment : Fragment() {

    private var _binding: FragmentCreatePrizeBinding? = null

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
        _binding = FragmentCreatePrizeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/
}