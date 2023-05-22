package com.andes.vinilos.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentCreateArtistBinding
import com.andes.vinilos.databinding.FragmentCreatePrizeBinding
import com.andes.vinilos.models.Album
import com.andes.vinilos.models.Musician
import com.andes.vinilos.models.Prize
import com.andes.vinilos.viewmodels.SaveAlbumViewModel
import com.andes.vinilos.viewmodels.SaveArtistViewModel
import com.andes.vinilos.viewmodels.SavePrizeViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class SavePrizeFragment: Fragment() {
    private var _binding: FragmentCreatePrizeBinding? = null
    private lateinit var savePrizeViewModel: SavePrizeViewModel

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePrizeBinding.inflate(inflater, container, false)
        var btnSavePrize = binding.guardarCrearPrize
        var btnCancelPrize = binding.cancelarCrearPrize


        btnSavePrize.setOnClickListener {
            val prizeName = binding.prizeName.text.toString().trim()
            val prizeDescription = binding.prizeDescription.text.toString().trim()
            val prizeOrganization = binding.prizeOrganization.text.toString().trim()

            if (prizeName.isEmpty()) {
                binding.prizeName.error = "El campo nombre es obligatorio"
                return@setOnClickListener
            }
            if (prizeName.length < 3 || prizeName.length > 50) {
                binding.prizeName.error = "El campo Nombre debe tener entre 3 y 50 caracteres"
                return@setOnClickListener
            }
            if (prizeDescription.isEmpty()) {
                binding.prizeDescription.error = "El campo Descripción es obligatorio"
                return@setOnClickListener
            }
            if (prizeDescription.length < 3 || prizeDescription.length > 50) {
                binding.prizeDescription.error =
                    "El campo Descripción debe tener entre 3 y 50 caracteres"
                return@setOnClickListener
            }


            val prize = Prize(
                id = 0,
                name = prizeName,
                description = prizeDescription,
                organization = prizeOrganization
            )
            savePrize(prize)
        }

        btnCancelPrize.setOnClickListener {
            findNavController().navigate(R.id.action_savePrizeFragment_to_navigation_artist)
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        savePrizeViewModel = ViewModelProvider(this).get(SavePrizeViewModel::class.java)

        savePrizeViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }


    private fun savePrize(prize: Prize) {
        lifecycleScope.launch {
            savePrizeViewModel.savePrize(prize,
                onSuccess = {
                    val toast = Toast.makeText(requireContext(), "Se ha creado el premios exitosamente", Toast.LENGTH_LONG)
                    toast.show()
                    findNavController().navigate(R.id.action_savePrizeFragment_to_navigation_artist)
                },
                onError = {
                    val toast = Toast.makeText(requireContext(), "No se pudo guardar el premio en estos momentos", Toast.LENGTH_LONG)
                    toast.show()
                }
            )
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!savePrizeViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            savePrizeViewModel.onNetworkErrorShown()
        }
    }
}
