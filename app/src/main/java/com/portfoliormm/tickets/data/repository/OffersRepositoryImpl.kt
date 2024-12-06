package com.portfoliormm.tickets.data.repository

import com.portfoliormm.tickets.data.api.OffersApi
import com.portfoliormm.tickets.data.model.OffersResponse
import com.portfoliormm.tickets.domain.model.TicketOffer
import com.portfoliormm.tickets.domain.repository.OffersRepository
import com.google.gson.Gson
import com.portfoliormm.tickets.data.model.OfferTicketsResponse
import com.portfoliormm.tickets.data.model.TicketsResponse
import com.portfoliormm.tickets.domain.model.OfferTickets
import com.portfoliormm.tickets.domain.model.TicketDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OffersRepositoryImpl(
    private val api: OffersApi,
    private val gson: Gson
) : OffersRepository {
    override suspend fun getOffers(url: String): List<TicketOffer> = withContext(Dispatchers.IO) {
        val response = api.getOffers(url)
        val jsonString = response.string()
        val offersResponse = gson.fromJson(jsonString, OffersResponse::class.java)

        offersResponse.offers.map { offer ->
            TicketOffer(
                id = offer.id,
                title = offer.title,
                town = offer.town,
                price = offer.price.value
            )
        }
    }

    override suspend fun getOfferTickets(url: String): List<OfferTickets> = withContext(Dispatchers.IO){
        val response = api.getOfferTickets(url)
        val jsonString = response.string()
        val offerTicketsResponse = gson.fromJson(jsonString, OfferTicketsResponse::class.java)

        offerTicketsResponse.tickets_offers.map { offer ->
            OfferTickets(
                id = offer.id,
                title = offer.title,
                time_range = offer.time_range,
                price = offer.price.value
            )
        }
    }

    override suspend fun getTickets(url: String): List<TicketDomain> = withContext(Dispatchers.IO){
        val response = api.getTickets(url)
        val jsonString = response.string()
        val ticketsResponse = gson.fromJson(jsonString, TicketsResponse::class.java)

        ticketsResponse.tickets.map { ticket -> TicketDomain.from(ticket) }
    }
}
