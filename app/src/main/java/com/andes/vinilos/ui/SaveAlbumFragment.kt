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

        saveAlbumButton.setOnClickListener{
            Log.d("llego", "llego")
            // Catch value objects
            var albumNameElement: EditText = binding.nombreAlbum
            var albumNameWritten = albumNameElement.text.toString()

            var albumCoverElement: EditText = binding.cover
            var albumCoverWritten = albumCoverElement.text.toString()

            var albumGenresElement: Spinner = binding.albumGeneros
            var albumGenreSelected = albumGenresElement.selectedItem.toString()

            var albumRecordLabelElement: Spinner = binding.etiquetasGrabaciones
            var albumRecordLabelSelected = albumRecordLabelElement.selectedItem.toString()

            var albumDescriptionElement: EditText = binding.descripcionAlbum
            var albumDescriptionWritten = albumDescriptionElement.text.toString()

            var button: Button = binding.datePickerButton

            if(albumNameWritten.isEmpty()){
                albumNameElement.error = "El campo nombre es obligatorio"
                return@setOnClickListener
            }
            if(albumCoverWritten.isEmpty()){
                albumCoverElement.error = "El campo cover es obligatorio"
                return@setOnClickListener
            }

            if (albumNameWritten.length < 5 || albumNameWritten.length > 50) {
                albumNameElement.error = "El campo nombre debe tener entre 5 y 50 caracteres"
                return@setOnClickListener
            }

            if(albumDescriptionWritten.isEmpty()){
                albumDescriptionElement.error = "El campo descripcion es obligatorio"
                return@setOnClickListener
            }

            if (albumDescriptionWritten.length < 5 || albumDescriptionWritten.length > 50) {
                albumDescriptionElement.error = "La descripción debe tener entre 5 y 50 caracteres"
                return@setOnClickListener
            }

            if(albumDescriptionWritten.isEmpty()){
                albumDescriptionElement.error = "El campo descripcion es obligatorio"
                return@setOnClickListener
            }

            if (!dateSelected) {
                button.error = "Campo fecha obligatorio"
                return@setOnClickListener
            } else {
                button.error = null
            }

            val albumDateFormat = SimpleDateFormat("dd-MM-yyyy")
            val albumDate = albumDateFormat.format(selectedDate.time)

            val album = Album(
                albumNameWritten,
                albumCoverWritten,
                albumDate,
                albumDescriptionWritten,
                albumGenreSelected,
                albumRecordLabelSelected
            )

            saveAlbum(album)

        }

        cancelAlbumButton.setOnClickListener{
            Log.d("pppppp", "ppppppp")
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

    private fun saveAlbum(album: Album) {
        lifecycleScope.launch {
            saveAlbumViewModel.saveAlbum(album,
                onSuccess = {
                    val toast = Toast.makeText(requireContext(), "Se ha creado el album exitosamente", Toast.LENGTH_LONG)
                    toast.show()
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