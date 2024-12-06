package com.portfoliormm.tickets.domain.usecase

import com.portfoliormm.tickets.domain.model.TicketDomain
import com.portfoliormm.tickets.domain.repository.OffersRepository

class GetTicketsUseCase(private val repository: OffersRepository) {
    suspend operator fun invoke(url: String): List<TicketDomain> {
        return repository.getTickets(url)
    }
}
