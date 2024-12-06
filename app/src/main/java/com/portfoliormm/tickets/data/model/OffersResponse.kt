package com.portfoliormm.tickets.data.model

data class OffersResponse(
    val offers: List<OfferDto>
)

data class OfferDto(
    val id: Int,
    val title: String,
    val town: String,
    val price: PriceDto
)

data class PriceDto(
    val value: Int
)
