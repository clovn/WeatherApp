package com.example.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.api.AuthResult
import com.example.impl.domain.IsAuthUseCase
import com.example.impl.domain.RegisterUseCase
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class RegisterViewModel(
    private val register: RegisterUseCase,
    private val isAuth: IsAuthUseCase
): ViewModel(), ContainerHost<RegisterState, Nothing> {
    override val container = container<RegisterState, Nothing>(RegisterState.Idle)

    init {
        intent {
            if (isAuth.invoke()) reduce { RegisterState.Success }
        }

        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "RegisterScreen")
        }
    }
    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Register -> register(event.email, event.password)
        }
    }

    private fun register(email: String, password: String) = intent {
        reduce { RegisterState.Loading }
        when(val result = register.invoke(email, password)){
            is AuthResult.Error -> {
                reduce { RegisterState.Error(message = result.message) }
            }
            is AuthResult.Success -> {
                reduce { RegisterState.Success }
            }
        }
    }
}

sealed class RegisterState{
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

sealed class AuthEvent {
    data class Register(val email: String, val password: String) : AuthEvent()
}