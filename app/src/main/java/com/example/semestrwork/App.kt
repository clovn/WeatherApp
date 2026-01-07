package com.example.semestrwork

import android.app.Application
import com.example.impl.di.addModule
import com.example.impl.di.authModule
import com.example.impl.di.homeModule
import com.example.impl.di.pickModule
import com.example.local.localModule
import com.example.network.networkModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, homeModule, localModule, pickModule, addModule, authModule))
        }

    }
}
