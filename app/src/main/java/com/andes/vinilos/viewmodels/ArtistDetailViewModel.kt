package com.andes.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.andes.vinilos.database.VinilosRoomDatabase
import com.andes.vinilos.models.Musician
import com.andes.vinilos.models.Prize
import com.andes.vinilos.repositories.PrizeRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistDetailViewModel(
    application: Application,
    private val dispatcherProvider: CoroutineDispatcherProvider
) : AndroidViewModel(application) {
    private val _artistMutableData = MutableLiveData<Musician>()
    // MutableLiveData to hold the list of prizes retrieved from the repository
    private val _prizes = MutableLiveData<List<Prize>>(listOf())
    val artist: LiveData<Musician>
        get() = _artistMutableData

    // MutableLiveData to hold the list of prizes retrieved from the repository
    val prizes: LiveData<List<Prize>>
        get() = _prizes

    // Comparator to sort the list of albums by name in ascending order
    private val prizeNameComparator = compareBy<Prize> { it.name.lowercase() }

    // MutableLiveData to track network errors
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    // MutableLiveData to track whether the network error has been shown to the user
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    fun refreshData(currentArtist: Musician) {
        _artistMutableData.postValue(currentArtist)
    }

    // Repository to retrieve album data from the network
    private val prizesRepository = PrizeRepository(
        application,
        VinilosRoomDatabase.getDatabase(application.applicationContext).prizesDao()
    )

    // Initialize the ViewModel by refreshing data from the network
    init {
        refreshDataFromNetwork()
    }
    // Refresh album data from the network
    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(dispatcherProvider.default()) {
                withContext(dispatcherProvider.io()) {
                    var data = prizesRepository.refreshData().sortedWith(prizeNameComparator)
                    _prizes.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch (e: Exception) {
            _eventNetworkError.value = true
        }
    }

    class Factory(
        private val app: Application,
        private val dispatcherProvider: CoroutineDispatcherProvider
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel(app,dispatcherProvider) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}