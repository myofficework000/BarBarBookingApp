package com.example.barbarbookingapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.barbarbookingapp.view.intro_screens.Login
import com.example.barbarbookingapp.view.intro_screens.SignUp
import com.example.barbarbookingapp.view.intro_screens.SplashScreen
import com.example.barbarbookingapp.view.service_screens.SelectServiceScreen
import com.example.barbarbookingapp.view.service_screens.SelectTimeSlotScreen
import com.example.barbarbookingapp.viewmodel.AppointmentViewModel

@Composable
fun MyApp() {

    val navController = rememberNavController()
    val viewModel: AppointmentViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.SelectService.route
    ) {
        composable(route = Screen.Splash.route) { SplashScreen(navController) }
        composable(route = Screen.Login.route) { Login(navController) }
        composable(route = Screen.SignUp.route) { SignUp(navController) }
        composable(route = Screen.SelectTimeSlot.route) { SelectTimeSlotScreen(viewModel) }
        composable(route = Screen.SelectService.route) { SelectServiceScreen(viewModel,navController) }
    }
}