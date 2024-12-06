package com.portfoliormm.tickets.di

import com.portfoliormm.tickets.data.local.PreferencesManager
import com.portfoliormm.tickets.presentation.ui.CountrySearchViewModel
import com.portfoliormm.tickets.presentation.ui.TicketViewingViewModel
import com.portfoliormm.tickets.presentation.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single { PreferencesManager(androidContext()) }

    viewModel {
        MainViewModel(get(), get())
    }
    viewModel {
        CountrySearchViewModel(get(), get())
    }
    viewModel {
        TicketViewingViewModel(get(), get())
    }
}
