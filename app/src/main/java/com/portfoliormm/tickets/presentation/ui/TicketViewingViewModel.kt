package com.portfoliormm.tickets.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfoliormm.tickets.data.local.PreferencesManager
import com.portfoliormm.tickets.domain.model.TicketDomain
import com.portfoliormm.tickets.domain.usecase.GetTicketsUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TicketViewingViewModel(
    private val preferencesManager: PreferencesManager,
    private val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {

    private val _routeText = MutableLiveData<String>()
    val routeText: LiveData<String> = _routeText

    private val _detailsText = MutableLiveData<String>()
    val detailsText: LiveData<String> = _detailsText

    private val _tickets = MutableLiveData<List<TicketDomain>>()
    val tickets: LiveData<List<TicketDomain>> = _tickets

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val dateFormat = SimpleDateFormat("dd MMMM", Locale("ru"))

    private val url = "https://drive.google.com/uc?export=download&id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA"

    init {
        loadRouteInfo()
        loadTickets()
    }

    private fun loadRouteInfo() {
        val departure = preferencesManager.getLastDeparture()
        val arrival = preferencesManager.getLastArrival()
        _routeText.value = "$departure-$arrival"

        val calendar = Calendar.getInstance().apply {
            timeInMillis = preferencesManager.getDepartureDate()
        }
        _detailsText.value = "${dateFormat.format(calendar.time)}, 1 пассажир"
    }

    private fun loadTickets() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val tickets = getTicketsUseCase(url)
                _tickets.value = tickets
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }
}
