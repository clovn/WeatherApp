package com.example.semestrwork.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.impl.presentation.AddCityScreen
import com.example.impl.presentation.HourlyWeatherScreen
import com.example.impl.presentation.LoginScreen
import com.example.impl.presentation.PickScreen
import com.example.impl.presentation.RegisterScreen
import com.example.impl.presentation.WeatherScreen

@Composable
fun navGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Destination.Login.route
    ){
        composable(Destination.Home.route) {
            WeatherScreen(
                navigateToDetail = {
                    navController.navigateToDetail()
                },
                navigateToLogin = {
                    navController.navigateToLogin()
                }
            ) {
                navController.navigateToPick()
            }
        }
        composable(Destination.Pick.route){
            PickScreen (
                navigateToMain = { navController.navigateToHome() },
                navigateToAdd = { navController.navigateToAdd() }
            )
        }
        composable(Destination.Add.route) {
            AddCityScreen {
                navController.navigateToPick()
            }
        }

        composable(Destination.Register.route){
            RegisterScreen(navigateToHome = {
                navController.navigateToHome()
            }) {
                navController.navigateToLogin()
            }
        }

        composable(Destination.Login.route) {
            LoginScreen(navigateToHome = {
                navController.navigateToHome()
            }) {
                navController.navigateToRegister()
            }
        }

        composable(Destination.Detail.route) {
            HourlyWeatherScreen()
        }
    }
}
