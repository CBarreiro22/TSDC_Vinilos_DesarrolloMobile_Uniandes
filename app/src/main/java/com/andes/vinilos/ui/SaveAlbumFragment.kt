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
import com.andes.vinilos.R
import com.andes.vinilos.databinding.FragmentCreateAlbumBinding
import com.andes.vinilos.viewmodels.OptionsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class SaveAlbumFragment : Fragment() {

    private var _binding: FragmentCreateAlbumBinding? = null

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

            if (albumCoverWritten.length < 5 || albumCoverWritten.length > 50) {
                albumCoverElement.error = "El campo cover debe tener entre 5 y 50 caracteres"
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



        }

        cancelAlbumButton.setOnClickListener{
            Log.d("pppppp", "ppppppp")
        }

        return root
    }

    private fun onDatePickerButtonClick(view: View) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(view.context, DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            // Update text of the button with the selected date
            val selectedDate = "$selectedDayOfMonth-${selectedMonth+1}-$selectedYear"
            (view as Button).text = selectedDate
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