package com.portfoliormm.tickets.data.model

data class OfferTicketsResponse(
    val tickets_offers: List<TicketOffer>
)

data class TicketOffer(
    val id: Int,
    val title: String,
    val time_range: List<String>,
    val price: Price
)

data class Price(
    val value: Int
)
