package com.portfoliormm.tickets.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfoliormm.tickets.data.local.PreferencesManager
import com.portfoliormm.tickets.domain.model.TicketOffer
import com.portfoliormm.tickets.domain.usecase.GetOffersUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getOffersUseCase: GetOffersUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _offers = MutableLiveData<List<TicketOffer>>()
    val offers: LiveData<List<TicketOffer>> = _offers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _lastDeparture = MutableLiveData<String>()
    val lastDeparture: LiveData<String> = _lastDeparture

    private val offersUrl = "https://drive.usercontent.google.com/u/0/uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav&export=download"

    init {
        loadLastDeparture()
    }

    fun loadOffers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = getOffersUseCase(offersUrl)
                _offers.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveDeparture(departure: String) {
        preferencesManager.saveLastDeparture(departure)
        _lastDeparture.value = departure
    }

    private fun loadLastDeparture() {
        _lastDeparture.value = preferencesManager.getLastDeparture()
    }

    fun saveArrival(arrival: String) {
        preferencesManager.saveLastArrival(arrival)
    }
}
