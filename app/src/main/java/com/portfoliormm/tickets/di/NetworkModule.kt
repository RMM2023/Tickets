package com.portfoliormm.tickets.di

import com.portfoliormm.tickets.data.api.OffersApi
import com.portfoliormm.tickets.data.repository.OffersRepositoryImpl
import com.portfoliormm.tickets.domain.repository.OffersRepository
import com.portfoliormm.tickets.domain.usecase.GetTicketsUseCase
import com.google.gson.Gson
import com.portfoliormm.tickets.domain.usecase.GetOfferTicketsUseCase
import com.portfoliormm.tickets.domain.usecase.GetOffersUseCase
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Gson()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://placeholder.com/")
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(OffersApi::class.java)
    }

    single<OffersRepository> {
        OffersRepositoryImpl(get(), get())
    }

    single {
        GetOffersUseCase(get())
    }

    single {
        GetOfferTicketsUseCase(get())
    }

    single {
        GetTicketsUseCase(get())
    }
}
