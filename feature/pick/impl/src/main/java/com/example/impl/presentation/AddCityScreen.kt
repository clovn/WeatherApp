package com.example.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun AddCityScreen(
    viewModel: AddCityViewModel = koinInject(),
    navigateToPick: () -> Unit
) {
    var cityName by remember { mutableStateOf(TextFieldValue("")) }

    val state by viewModel.collectAsState()

    when(state){
        is AddCityState.NavigateToPick -> {
            navigateToPick()
        }
        is AddCityState.Error -> {
            Text(text = "Error: ${(state as AddCityState.Error).error}", color = Color.Red)
        }
        is AddCityState.Idle -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = cityName,
                    onValueChange = { cityName = it },
                    label = { Text("Введите название города") },
                    placeholder = { Text("Например, Москва") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (cityName.text.isNotBlank()) {
                            viewModel.addCity(cityName.text)
                            cityName = TextFieldValue("")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(text = "Добавить город")
                }
            }
        }
    }
}