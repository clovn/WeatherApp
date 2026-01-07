package com.example.impl.di


import com.example.impl.domain.LoadCitiesUseCase
import com.example.impl.domain.SaveSelectionUseCase
import com.example.impl.presentation.PickScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val pickModule = module {
    viewModel { PickScreenViewModel(saveSelection = get(), loadCities = get()) }
    single { LoadCitiesUseCase(get()) }
    single { SaveSelectionUseCase(get()) }
}