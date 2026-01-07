package com.example.impl.di

import com.example.api.AuthRepository
import com.example.impl.data.AuthRepositoryImpl
import com.example.impl.domain.IsAuthUseCase
import com.example.impl.domain.LoginUseCase
import com.example.impl.domain.RegisterUseCase
import com.example.impl.presentation.LoginViewModel
import com.example.impl.presentation.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single {
        RegisterUseCase(get())
    }

    single {
        LoginUseCase(get())
    }

    single {
        IsAuthUseCase(get())
    }

    viewModel{
        RegisterViewModel(get(), get())
    }
    viewModel{
        LoginViewModel(get(), get())
    }
}