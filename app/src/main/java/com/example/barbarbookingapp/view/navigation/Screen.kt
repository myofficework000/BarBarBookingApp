package com.example.barbarbookingapp.view.navigation

import com.example.barbarbookingapp.view.navigation.NavRoutes.LOGIN
import com.example.barbarbookingapp.view.navigation.NavRoutes.SIGNUP
import com.example.barbarbookingapp.view.navigation.NavRoutes.SPLASH

sealed class Screen(val route: String) {
    object Splash : Screen(SPLASH)
    object Login : Screen(LOGIN)
    object SignUp : Screen(SIGNUP)
}
