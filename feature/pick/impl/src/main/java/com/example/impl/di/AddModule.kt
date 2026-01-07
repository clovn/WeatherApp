package com.example.impl.di

import com.example.impl.domain.AddCityUseCase
import com.example.impl.presentation.AddCityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val addModule = module {
    viewModel { AddCityViewModel(get()) }
    single { AddCityUseCase(get()) }
}