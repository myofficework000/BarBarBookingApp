package com.example.barbarbookingapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.barbarbookingapp.view.appointment_screens.AppointmentDetails
import com.example.barbarbookingapp.view.appointment_screens.AppointmentList
import com.example.barbarbookingapp.view.appointment_screens.SalonInformation
import com.example.barbarbookingapp.view.intro_screens.DashboardScreen
import com.example.barbarbookingapp.view.intro_screens.Login
import com.example.barbarbookingapp.view.intro_screens.SignUp
import com.example.barbarbookingapp.view.intro_screens.SplashScreen
import com.example.barbarbookingapp.view.service_screens.SelectServiceScreen
import com.example.barbarbookingapp.view.service_screens.SelectTimeSlotScreen
import com.example.barbarbookingapp.viewmodel.BarberViewModel
import com.example.barbarbookingapp.viewmodel.UserViewModel

@Composable
fun MyApp() {

    val navController = rememberNavController()
    val viewModel: BarberViewModel = hiltViewModel()
    val userViewModel:UserViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) { SplashScreen(navController) }
        composable(route = Screen.Login.route) { Login(navController, userViewModel) }
        composable(route = Screen.SignUp.route) { SignUp(navController) }
        composable(route = Screen.SelectTimeSlot.route) { SelectTimeSlotScreen(viewModel, navController) }
        composable(route = Screen.SelectService.route) { SelectServiceScreen(viewModel,navController) }
        composable(route = Screen.DashboardScreen.route) { DashboardScreen(navController) }
        composable(route = Screen.SalonInformation.route) { SalonInformation(navController)}
        composable(route = Screen.AppointmentList.route) { AppointmentList(viewModel = viewModel, userId = 1, navController) }
        composable(
            route = "${NavRoutes.APPOINTMENT_DETAILS}/{appointmentId}",
            arguments = listOf(navArgument("appointmentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getInt("appointmentId") ?: 0
            AppointmentDetails(viewModel = viewModel, appointmentId = appointmentId, navController = navController)
        }
    }
}