package com.example.impl.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun PickScreen(
    viewModel: PickScreenViewModel = koinInject(),
    navigateToMain: () -> Unit,
    navigateToAdd: () -> Unit
) {
    val state by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCities()
    }


    Column (
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .padding(32.dp)
    ) {
        when(state){
            is PickState.Loading -> {
                CircularProgressIndicator()
            }
            is PickState.Error -> {
                Text(text = "Error: ${(state as PickState.Error).error}", color = Color.Red)
            }
            is PickState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items((state as PickState.Success).cities) { city ->
                        CityCard(city = city, onClick = {
                            viewModel.onCityCardClicked(city)
                            navigateToMain()
                        })
                    }
                }

                Button(
                    onClick = { navigateToAdd() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(56.dp)
                ) {
                    Text(text = "Add New")
                }
            }
        }
    }
}

@Composable
fun CityCard(city: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = city,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}