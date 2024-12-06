package com.portfoliormm.tickets.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfoliormm.tickets.data.local.PreferencesManager
import com.portfoliormm.tickets.domain.model.OfferTickets
import com.portfoliormm.tickets.domain.usecase.GetOfferTicketsUseCase
import kotlinx.coroutines.launch
import java.util.*

class CountrySearchViewModel(
    private val getOfferTicketsUseCase: GetOfferTicketsUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _fromCity = MutableLiveData<String>()
    val fromCity: LiveData<String> = _fromCity

    private val _toCity = MutableLiveData<String>()
    val toCity: LiveData<String> = _toCity

    private val _departureDate = MutableLiveData<Calendar>()
    val departureDate: LiveData<Calendar> = _departureDate

    private val _tickets = MutableLiveData<List<OfferTickets>>()
    val tickets: LiveData<List<OfferTickets>> = _tickets

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val ticketsUrl = "https://drive.usercontent.google.com/u/0/uc?id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta&export=download"

    init {
        loadCities()
        loadDepartureDate()
        loadTickets()
    }

    private fun loadCities() {
        _fromCity.value = preferencesManager.getLastDeparture()
        _toCity.value = preferencesManager.getLastArrival()
    }

    private fun loadDepartureDate() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = preferencesManager.getDepartureDate()
        _departureDate.value = calendar
    }

    fun saveDepartureDate(calendar: Calendar) {
        preferencesManager.saveDepartureDate(calendar.timeInMillis)
        _departureDate.value = calendar
    }

    fun saveCities(from: String, to: String) {
        preferencesManager.saveLastDeparture(from)
        preferencesManager.saveLastArrival(to)
        _fromCity.value = from
        _toCity.value = to
    }

    fun loadTickets() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = getOfferTicketsUseCase(ticketsUrl)
                _tickets.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
