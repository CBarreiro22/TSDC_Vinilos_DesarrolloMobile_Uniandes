package com.andes.vinilos.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.provider.MediaStore.Audio.Artists
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
import com.andes.vinilos.models.Musician
import com.andes.vinilos.viewmodels.SaveArtistViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class SaveArtistFragment : Fragment() {
    private var _binding: FragmentCreateArtistBinding? = null
    private lateinit var saveArtistViewModel: SaveArtistViewModel
    private var selectedDate: Calendar = Calendar.getInstance()
    private var dateSelected = false
    private val binding get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        saveArtistViewModel = ViewModelProvider(this).get(SaveArtistViewModel::class.java)
        saveArtistViewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArtistBinding.inflate(inflater, container, false)
        var btnSaveArtist = binding.guardarCrearArtista
        var btnCancelArtist = binding.cancelarCrearArtista
        var dpkFechaCumpleanos = binding.artistBirthday

        btnSaveArtist.setOnClickListener {
            val artistName = binding.artistName.text.toString().trim()
            val artistDescription = binding.artistDescription.text.toString().trim()
            val artistUrlImage = binding.artistImage.text.toString().trim()
            if (artistName.isEmpty()) {
                binding.artistName.error = "El campo nombre es obligatorio"
                return@setOnClickListener
            }
            if (artistName.length < 3 || artistName.length > 50) {
                binding.artistName.error = "El campo Nombre debe tener entre 3 y 50 caracteres"
                return@setOnClickListener
            }
            if (artistDescription.isEmpty()) {
                binding.artistDescription.error = "El campo Descripción es obligatorio"
                return@setOnClickListener
            }
            if (artistDescription.length < 3 || artistDescription.length > 50) {
                binding.artistDescription.error =
                    "El campo Descripción debe tener entre 3 y 50 caracteres"
                return@setOnClickListener
            }
            if (artistUrlImage.isEmpty()) {
                binding.artistImage.error = "El campo Imagen Url es obligatorio"
                return@setOnClickListener
            }
            val brithDate = SimpleDateFormat("yyyy-MM-dd").format(selectedDate.time)

            val artist = Musician(
                name = artistName,
                description = artistDescription,
                image = artistUrlImage,
                birthDate = brithDate
            )
            lifecycleScope.launch {
                saveArtistViewModel.saveArtist(artist, {
                    val toast = Toast.makeText(
                        requireContext(), "Se ha creado el artista exitosamente", Toast.LENGTH_LONG
                    )
                    toast.show()
                    findNavController().navigate(R.id.action_saveArtistFragment_to_navigation_artist)
                }, {
                    val toast = Toast.makeText(
                        requireContext(),
                        "No se pudo guardar el artista en estos momentos",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                })
            }
        }

        btnCancelArtist.setOnClickListener {
            findNavController().navigate(R.id.action_saveArtistFragment_to_navigation_artist)
        }

        dpkFechaCumpleanos.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val day = currentDate.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                it.context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
                    (it as Button).text = "$selectedDayOfMonth-${selectedMonth + 1}-$selectedYear"
                    dateSelected = true
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
            dateSelected = false
        }

        return binding.root
    }

    private fun onNetworkError() {
        if (!saveArtistViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            saveArtistViewModel.onNetworkErrorShown()
        }
    }
}