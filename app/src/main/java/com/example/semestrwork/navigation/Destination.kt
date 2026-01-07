package com.example.semestrwork.navigation

sealed class Destination(val route: String) {
    object Home: Destination("home")
    object Pick: Destination("pick")
    object Add: Destination("add")
    object Login: Destination("login")
    object Register: Destination("register")
    object Detail: Destination("detail")
}
