package com.example.impl.presentation

import androidx.lifecycle.ViewModel
import com.example.api.AuthResult
import com.example.impl.domain.IsAuthUseCase
import com.example.impl.domain.LoginUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class LoginViewModel (
    private val login: LoginUseCase,
    private val isAuth: IsAuthUseCase
): ViewModel(), ContainerHost<LoginState, Nothing> {
    override val container = container<LoginState, Nothing>(LoginState.Idle)

    init {
        intent {
            if (isAuth.invoke()) reduce { LoginState.Success }
        }
    }
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)
        }
    }

    private fun login(email: String, password: String) = intent {
        reduce { LoginState.Loading }
        when(val result = login.invoke(email, password)){
            is AuthResult.Error -> {
                reduce { LoginState.Error(message = result.message) }
            }
            is AuthResult.Success -> {
                reduce { LoginState.Success }
            }
        }
    }
}

sealed class LoginState{
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent()
}