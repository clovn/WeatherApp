package com.example.impl.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.liveData
import org.checkerframework.checker.units.qual.Current
import org.koin.compose.koinInject
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinInject(),
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit
) {
    val state by viewModel.collectAsState()

    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.Error -> Toast.makeText(
                context,
                sideEffect.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    when (state) {
        is LoginState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is LoginState.Success -> navigateToHome()
        is LoginState.Idle -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = (state as LoginState.Idle).email,
                    onValueChange = { viewModel.onEvent(LoginEvent.ChangeEmail(it)) },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = (state as LoginState.Idle).password,
                    onValueChange = { viewModel.onEvent(LoginEvent.ChangePassword(it)) },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    viewModel.onEvent(
                        LoginEvent.Login(
                            (state as LoginState.Idle).email,
                            (state as LoginState.Idle).password
                        )
                    )
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Don't have an account? ",
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "Register here",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { navigateToRegister() }
                )
            }
        }
    }
}