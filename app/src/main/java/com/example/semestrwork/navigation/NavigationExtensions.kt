package com.example.semestrwork.navigation

import androidx.navigation.NavController

fun NavController.navigateToHome() {
    navigate(Destination.Home.route)
}

fun NavController.navigateToPick() {
    navigate(Destination.Pick.route)
}

fun NavController.navigateToAdd() {
    navigate(Destination.Add.route)
}

fun NavController.navigateToLogin() {
    navigate(Destination.Login.route)
}

fun NavController.navigateToRegister() {
    navigate(Destination.Register.route)
}