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
): ViewModel(), ContainerHost<RegisterState, AuthSideEffect> {
    override val container = container<RegisterState, AuthSideEffect>(RegisterState.Idle())

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
            is AuthEvent.ChangeEmail -> onChangeEmail(event.email)
            is AuthEvent.ChangePassword -> onChangePassword(event.password)
        }
    }

    private fun onChangeEmail(email: String) = intent {
        if (state is RegisterState.Idle) {
            reduce { (state as  RegisterState.Idle).copy(email = email) }
        }
    }

    private fun onChangePassword(password: String) = intent {
        if (state is RegisterState.Idle) {
            reduce { (state as  RegisterState.Idle).copy(password = password) }
        }
    }

    private fun register(email: String, password: String) = intent {
        reduce { RegisterState.Loading }
        when(val result = register.invoke(email, password)){
            is AuthResult.Error -> {
                reduce { RegisterState.Idle() }
                postSideEffect(AuthSideEffect.Error(message = result.message))
            }
            is AuthResult.Success -> {
                reduce { RegisterState.Success }
            }
        }
    }
}

sealed class RegisterState{
    data class Idle(val email: String = "", val password: String = "") : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
}

sealed class AuthEvent {
    data class Register(val email: String, val password: String) : AuthEvent()

    data class ChangeEmail(val email: String): AuthEvent()

    data class ChangePassword(val password: String): AuthEvent()
}

sealed class AuthSideEffect{
    data class Error(val message: String): AuthSideEffect()
}