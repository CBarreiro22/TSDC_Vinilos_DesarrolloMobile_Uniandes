package com.andes.vinilos.ui

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentCreateAlbumBinding
import com.andes.vinilos.models.Album
import com.andes.vinilos.viewmodels.OptionsViewModel
import com.andes.vinilos.viewmodels.SaveAlbumViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class SaveAlbumFragment : Fragment() {

    private var _binding: FragmentCreateAlbumBinding? = null
    private lateinit var saveAlbumViewModel: SaveAlbumViewModel
    private var selectedDate: Calendar = Calendar.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var dateSelected = false

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

        var saveAlbumButton: Button = binding.guardarCrearAlbum
        var cancelAlbumButton: Button = binding.cancelarCrearAlbum
        var datePickerButtom: Button = binding.datePickerButton

        datePickerButtom.setOnClickListener {
            onDatePickerButtonClick(it)
        }

        saveAlbumButton.setOnClickListener {
            // Obtener los valores ingresados por el usuario
            val albumName = binding.nombreAlbum.text.toString().trim()
            val albumCover = binding.cover.text.toString().trim()
            val albumDescription = binding.descripcionAlbum.text.toString().trim()
            val albumGenre = binding.albumGeneros.selectedItem.toString()
            val albumRecordLabel = binding.etiquetasGrabaciones.selectedItem.toString()

            // Verificar que se haya seleccionado una fecha
            if (!dateSelected) {
                binding.datePickerButton.error = "Campo fecha obligatorio"
                return@setOnClickListener
            } else {
                binding.datePickerButton.error = null
            }

            // Verificar que los campos obligatorios no estén vacíos
            if (albumName.isEmpty()) {
                binding.nombreAlbum.error = "El campo nombre es obligatorio"
                return@setOnClickListener
            }
            if (albumCover.isEmpty()) {
                binding.cover.error = "El campo cover es obligatorio"
                return@setOnClickListener
            }
            if (albumDescription.isEmpty()) {
                binding.descripcionAlbum.error = "El campo descripción es obligatorio"
                return@setOnClickListener
            }

            // Verificar que los campos de texto tengan la longitud adecuada
            if (albumName.length < 5 || albumName.length > 50) {
                binding.nombreAlbum.error = "El campo nombre debe tener entre 5 y 50 caracteres"
                return@setOnClickListener
            }
            if (albumDescription.length < 5 || albumDescription.length > 50) {
                binding.descripcionAlbum.error = "El campo descripción debe tener entre 5 y 50 caracteres"
                return@setOnClickListener
            }

            val albumDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val albumDate = albumDateFormat.format(selectedDate.time)
            // Crear un objeto Album a partir de los valores ingresados
            val album = Album(
                id = 0, // Esto se asignará automáticamente en la base de datos
                name = albumName,
                cover = albumCover,
                releaseDate = albumDate,
                description = albumDescription,
                genre = albumGenre,
                recordLabel = albumRecordLabel
            )

            saveAlbum(album)

        }

        cancelAlbumButton.setOnClickListener{
            Log.d("cancelAlbumButton", "cancelAlbumButton")
            findNavController().navigate(R.id.action_saveAlbumFragment_to_navigation_albums)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        saveAlbumViewModel = ViewModelProvider(this).get(SaveAlbumViewModel::class.java)

        saveAlbumViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun onNetworkError() {
        if(!saveAlbumViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            saveAlbumViewModel.onNetworkErrorShown()
        }
    }

    private fun saveAlbum(Album: Album) {
        lifecycleScope.launch {
            saveAlbumViewModel.saveAlbum(Album,
                onSuccess = {
                    val toast = Toast.makeText(requireContext(), "Se ha creado el album exitosamente", Toast.LENGTH_LONG)
                    toast.show()
                    findNavController().navigate(R.id.action_saveAlbumFragment_to_navigation_albums)
                },
                onError = {
                    val toast = Toast.makeText(requireContext(), "No se pudo guardar el album en estos momentos", Toast.LENGTH_LONG)
                    toast.show()
                }
            )
        }
    }


    private fun onDatePickerButtonClick(view: View) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(view.context, DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            // Update text of the button with the selected date
            selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
            (view as Button).text = "$selectedDayOfMonth-${selectedMonth+1}-$selectedYear"
            dateSelected = true
        }, year, month, day)

        datePickerDialog.show()
        dateSelected = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}