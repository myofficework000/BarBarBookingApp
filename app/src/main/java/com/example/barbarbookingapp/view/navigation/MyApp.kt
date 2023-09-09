package com.example.barbarbookingapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.barbarbookingapp.view.intro_screens.Login
import com.example.barbarbookingapp.view.intro_screens.SignUp
import com.example.barbarbookingapp.view.intro_screens.SplashScreen

@Composable
fun MyApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) { SplashScreen(navController) }
        composable(route = Screen.Login.route) { Login(navController) }
        composable(route = Screen.SignUp.route) { SignUp(navController) }
    }
}