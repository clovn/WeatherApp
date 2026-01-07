package com.example.impl.presentation

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = koinInject(),
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val state by viewModel.collectAsState()



    when (state) {
        is RegisterState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is RegisterState.Success -> navigateToHome()
        is RegisterState.Idle -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = (state as RegisterState.Idle).email,
                    onValueChange = { viewModel.onEvent(AuthEvent.ChangeEmail(email = it)) },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = (state as RegisterState.Idle).password,
                    onValueChange = { viewModel.onEvent(AuthEvent.ChangePassword(password = it)) },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.onEvent(AuthEvent.Register((state as RegisterState.Idle).email, (state as RegisterState.Idle).password)) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Register")
                }

                Text("login", Modifier.clickable {
                    navigateToLogin()
                })

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}