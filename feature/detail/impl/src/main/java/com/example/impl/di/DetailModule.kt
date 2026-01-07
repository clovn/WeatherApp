package com.example.impl.di


import com.example.api.HourlyWeatherRepository
import com.example.impl.data.HourlyWeatherRepositoryImpl
import com.example.impl.domain.HourlyForecastUseCase
import com.example.impl.presentation.HourlyWeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel {
        HourlyWeatherViewModel(get())
    }

    single {
        HourlyForecastUseCase(get(), get())
    }

    single<HourlyWeatherRepository> {
        HourlyWeatherRepositoryImpl(get())
    }
}