package com.portfoliormm.tickets.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.portfoliormm.tickets.R
import com.portfoliormm.tickets.databinding.FlightsBinding
import com.portfoliormm.tickets.domain.model.OfferTickets
import java.text.NumberFormat
import java.util.Locale

class TicketOffersAdapter : RecyclerView.Adapter<TicketOffersAdapter.TicketOfferViewHolder>() {

    private val MAX_ITEMS = 3
    private val offers = mutableListOf<OfferTickets>()
    private val companyIcons = listOf(
        R.color.red,
        R.color.blue,
        R.color.white
    )

    fun setOffers(newOffers: List<OfferTickets>) {
        offers.clear()
        // Only take first 3 offers as per requirements
        offers.addAll(newOffers.take(MAX_ITEMS))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketOfferViewHolder {
        val binding = FlightsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketOfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketOfferViewHolder, position: Int) {
        holder.bind(offers[position], position)
    }

    override fun getItemCount(): Int = minOf(offers.size, MAX_ITEMS)

    inner class TicketOfferViewHolder(
        private val binding: FlightsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(offer: OfferTickets, position: Int) {
            with(binding) {
                companyIcon.setImageResource(companyIcons[position])
                companyName.text = offer.title
                timeRange.text = offer.time_range.joinToString(" ")

                // Format price with thousand separators
                val formattedPrice = NumberFormat.getNumberInstance(Locale("ru")).format(offer.price)
                price.text = "${formattedPrice} ₽"
            }
        }
    }
}
