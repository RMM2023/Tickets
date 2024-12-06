package com.portfoliormm.tickets.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfoliormm.tickets.databinding.ItemTicketViewBinding
import com.portfoliormm.tickets.domain.model.TicketDomain

class TicketViewAdapter : ListAdapter<TicketDomain, TicketViewAdapter.TicketViewHolder>(TicketDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TicketViewHolder(
        private val binding: ItemTicketViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ticket: TicketDomain) {
            with(binding) {
                // Badge (optional)
                if (ticket.badge != null) {
                    ticketLabel.visibility = View.VISIBLE
                    ticketLabel.text = ticket.badge
                } else {
                    ticketLabel.visibility = View.GONE
                }

                // Price
                price.text = ticket.price

                // Departure and arrival times
                departureTime.text = ticket.departureTime
                arrivalTime.text = ticket.arrivalTime

                // Airport codes
                departureCode.text = ticket.departureAirportCode
                arrivalCode.text = ticket.arrivalAirportCode

                // Flight duration
                duration.text = ticket.flightDuration

                // Optional text (no transfers)
                if (ticket.optionalText != null) {
                    duration.text = ticket.flightDuration
                    transferInfo.text = ticket.optionalText
                } else {
                    duration.text = ticket.flightDuration
                    transferInfo.text = ""
                }
            }
        }
    }

    private class TicketDiffCallback : DiffUtil.ItemCallback<TicketDomain>() {
        override fun areItemsTheSame(oldItem: TicketDomain, newItem: TicketDomain): Boolean {
            return oldItem.departureTime == newItem.departureTime &&
                    oldItem.departureAirportCode == newItem.departureAirportCode &&
                    oldItem.arrivalTime == newItem.arrivalTime &&
                    oldItem.arrivalAirportCode == newItem.arrivalAirportCode
        }

        override fun areContentsTheSame(oldItem: TicketDomain, newItem: TicketDomain): Boolean {
            return oldItem == newItem
        }
    }
}
