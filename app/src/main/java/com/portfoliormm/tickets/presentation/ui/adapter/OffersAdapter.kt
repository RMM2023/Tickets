package com.portfoliormm.tickets.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.portfoliormm.tickets.R
import com.portfoliormm.tickets.databinding.OffersBinding
import com.portfoliormm.tickets.domain.model.TicketOffer

class OffersAdapter : RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    private val offers = mutableListOf<TicketOffer>()

    private val images = mapOf(
        1 to R.drawable._1,
        2 to R.drawable._2,
        3 to R.drawable._3
    )

    fun setOffers(newOffers: List<TicketOffer>) {
        offers.clear()
        offers.addAll(newOffers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = OffersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offers[position])
    }

    override fun getItemCount(): Int = offers.size

    inner class OfferViewHolder(
        private val binding: OffersBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(offer: TicketOffer) {
            with(binding) {
                textName.text = offer.title
                textCity.text = offer.town
                textCost.text = offer.price.toString() + " ₽"

                // Set image based on offer ID
                images[offer.id]?.let { imageResId ->
                    cityImage.setImageResource(imageResId)
                }
            }
        }
    }
}
