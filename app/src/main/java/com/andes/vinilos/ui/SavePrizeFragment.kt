package com.andes.vinilos.ui

import androidx.fragment.app.Fragment
import com.andes.vinilos.databinding.FragmentCreateArtistBinding
import com.andes.vinilos.viewmodels.SavePrizeViewModel
import java.util.Calendar

class SavePrizeFragment: Fragment() {
    private var _binding: FragmentCreateArtistBinding? = null
    private lateinit var savePrizeViewModel: SavePrizeViewModel
    private var selectedDate: Calendar = Calendar.getInstance()
    private var dateSelected = false
    private val binding get() = _binding!!
}
