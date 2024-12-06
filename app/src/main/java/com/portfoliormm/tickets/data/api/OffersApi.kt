package com.portfoliormm.tickets.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface OffersApi {
    @Streaming
    @GET
    suspend fun getOffers(@Url url: String): ResponseBody

    @Streaming
    @GET
    suspend fun getOfferTickets(@Url url: String): ResponseBody

    @Streaming
    @GET
    suspend fun getTickets(@Url url: String): ResponseBody
}
