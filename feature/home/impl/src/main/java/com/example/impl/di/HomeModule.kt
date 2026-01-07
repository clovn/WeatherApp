package com.example.impl.di


import com.example.api.LogoutUseCase
import com.example.api.WeatherRepository
import com.example.impl.data.WeatherRepositoryImpl
import com.example.impl.domain.WeatherUseCase
import org.koin.core.module.dsl.viewModel
import com.example.impl.presentation.WeatherViewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { WeatherViewModel(get(),get()) }
    single { LogoutUseCase(get()) }
    single { WeatherUseCase(get(), get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}