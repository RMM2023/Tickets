package com.portfoliormm.tickets.domain.model

data class OfferTickets(
    val id: Int,
    val title: String,
    val time_range: List<String>,
    val price: Int
)
