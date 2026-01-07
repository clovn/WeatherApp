package com.example.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.api.AuthResult
import com.example.impl.domain.IsAuthUseCase
import com.example.impl.domain.LoginUseCase
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class LoginViewModel (
    private val login: LoginUseCase,
    private val isAuth: IsAuthUseCase
): ViewModel(), ContainerHost<LoginState, LoginSideEffect> {
    override val container = container<LoginState, LoginSideEffect>(LoginState.Idle())

    init {
        intent {
            if (isAuth.invoke()) reduce { LoginState.Success }
        }
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "LoginScreen")
        }
    }
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)
            is LoginEvent.ChangeEmail -> onChangeEmail(event.email)
            is LoginEvent.ChangePassword -> onChangePassword(event.password)
        }
    }

    private fun onChangeEmail(email: String) = intent {
        if (state is LoginState.Idle) {
            reduce { (state as  LoginState.Idle).copy(email = email) }
        }
    }

    private fun onChangePassword(password: String) = intent {
        if (state is LoginState.Idle) {
            reduce { (state as  LoginState.Idle).copy(password = password) }
        }
    }
    private fun login(email: String, password: String) = intent {
        reduce { LoginState.Loading }
        when(val result = login.invoke(email, password)){
            is AuthResult.Error -> {
                postSideEffect(LoginSideEffect.Error(message = result.message))
                reduce { LoginState.Idle() }
            }
            is AuthResult.Success -> {
                reduce { LoginState.Success }
            }
        }
    }
}

sealed class LoginState{
    data class Idle(val email: String = "", val password: String = "") : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
}

sealed class LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent()
    data class ChangeEmail(val email: String): LoginEvent()
    data class ChangePassword(val password: String): LoginEvent()
}

sealed class LoginSideEffect{
    data class Error(val message: String): LoginSideEffect()
}