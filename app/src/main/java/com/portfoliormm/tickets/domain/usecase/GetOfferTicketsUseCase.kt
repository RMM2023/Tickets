package com.portfoliormm.tickets.domain.usecase

import com.portfoliormm.tickets.domain.model.OfferTickets
import com.portfoliormm.tickets.domain.repository.OffersRepository

class GetOfferTicketsUseCase(private val repository: OffersRepository) {
    suspend operator fun invoke(url: String): List<OfferTickets> {
        return repository.getOfferTickets(url)
    }
}
