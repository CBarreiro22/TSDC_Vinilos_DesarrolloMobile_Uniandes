package com.andes.vinilos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andes.vinilos.databinding.FragmentOptionsBinding
import com.andes.vinilos.viewmodels.OptionsViewModel
import android.widget.Button;

class OptionsFragment : Fragment() {

    private var _binding: FragmentOptionsBinding? = null

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

        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textDashboard
        //optionsViewModel.text.observe(viewLifecycleOwner) {
         //   textView.text = it
        //}
        val crearArtista: Button = binding.button4
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}