package com.portfoliormm.tickets.domain.repository

import com.portfoliormm.tickets.domain.model.OfferTickets
import com.portfoliormm.tickets.domain.model.TicketDomain
import com.portfoliormm.tickets.domain.model.TicketOffer

interface OffersRepository {
    suspend fun getOffers(url: String): List<TicketOffer>
    suspend fun getOfferTickets(url: String): List<OfferTickets>
    suspend fun getTickets(url: String): List<TicketDomain>
}
