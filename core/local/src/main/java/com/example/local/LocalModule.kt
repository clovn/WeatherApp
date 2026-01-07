package com.example.local

import org.koin.dsl.module

val localModule = module {
    single { SharedPrefsManager(get()) }
}