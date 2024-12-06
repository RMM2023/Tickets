package com.portfoliormm.tickets.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLastDeparture(departure: String) {
        prefs.edit().putString(KEY_LAST_DEPARTURE, departure).apply()
    }

    fun getLastDeparture(): String {
        return prefs.getString(KEY_LAST_DEPARTURE, "") ?: ""
    }

    fun saveLastArrival(arrival: String) {
        prefs.edit().putString(KEY_LAST_ARRIVAL, arrival).apply()
    }

    fun getLastArrival(): String {
        return prefs.getString(KEY_LAST_ARRIVAL, "") ?: ""
    }

    fun saveDepartureDate(timestamp: Long) {
        prefs.edit().putLong(KEY_DEPARTURE_DATE, timestamp).apply()
    }

    fun getDepartureDate(): Long {
        return prefs.getLong(KEY_DEPARTURE_DATE, System.currentTimeMillis())
    }

    companion object {
        private const val PREFS_NAME = "tickets_preferences"
        private const val KEY_LAST_DEPARTURE = "last_departure"
        private const val KEY_LAST_ARRIVAL = "last_arrival"
        private const val KEY_DEPARTURE_DATE = "departure_date"
    }
}
