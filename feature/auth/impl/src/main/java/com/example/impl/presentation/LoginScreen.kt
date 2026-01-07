package com.example.impl.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinInject(),
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit
) {
    val state by viewModel.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.onEvent(LoginEvent.Login(email, password)) }, modifier = Modifier.fillMaxWidth()) {
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

        when (state) {
            is LoginState.Loading -> CircularProgressIndicator()
            is LoginState.Success -> navigateToHome()
            is LoginState.Error -> Text((state as LoginState.Error).message)
            else -> {}
        }
    }
}