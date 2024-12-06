package com.portfoliormm.tickets.domain.usecase

import com.portfoliormm.tickets.domain.model.TicketOffer
import com.portfoliormm.tickets.domain.repository.OffersRepository

class GetOffersUseCase(private val repository: OffersRepository) {
    suspend operator fun invoke(url: String): List<TicketOffer> {
        return repository.getOffers(url)
    }
}
