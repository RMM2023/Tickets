package com.portfoliormm.tickets.domain.model

import com.portfoliormm.tickets.data.model.Ticket
import java.text.SimpleDateFormat
import java.util.*

data class TicketDomain(
    val badge: String?, // Бейдж, если присутствует
    val price: String, // Цена с символом рубля
    val departureTime: String, // Время отправления
    val departureAirportCode: String, // Код аэропорта отправления
    val arrivalTime: String, // Время прибытия
    val arrivalAirportCode: String, // Код аэропорта прибытия
    val flightDuration: String, // Время полета
    val optionalText: String? // "Без пересадок" или null
) {
    companion object {
        private const val RUB_SYMBOL = "₽"

        fun from(ticket: Ticket): TicketDomain {
            // Формат даты/времени
            val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            val departureDate = dateTimeFormat.parse(ticket.departure.date)
            val arrivalDate = dateTimeFormat.parse(ticket.arrival.date)

            // Форматирование времени
            val departureTime = timeFormat.format(departureDate)
            val arrivalTime = timeFormat.format(arrivalDate)

            // Расчет длительности полета
            val durationMillis = arrivalDate.time - departureDate.time
            val hours = durationMillis / (1000 * 60 * 60)
            val minutes = (durationMillis / (1000 * 60)) % 60
            val flightDuration = "${hours}ч ${minutes}м"

            return TicketDomain(
                badge = ticket.badge, // Бейдж или null
                price = "${ticket.price.value} $RUB_SYMBOL", // Цена + символ рубля
                departureTime = departureTime, // Время отправления
                departureAirportCode = ticket.departure.airport, // Код аэропорта отправления
                arrivalTime = arrivalTime, // Время прибытия
                arrivalAirportCode = ticket.arrival.airport, // Код аэропорта прибытия
                flightDuration = flightDuration, // Длительность полета
                optionalText = if (!ticket.has_transfer) "Без пересадок" else null // Опциональный текст
            )
        }
    }
}
