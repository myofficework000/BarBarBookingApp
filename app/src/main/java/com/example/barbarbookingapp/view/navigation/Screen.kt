package com.example.barbarbookingapp.view.navigation

import com.example.barbarbookingapp.view.navigation.NavRoutes.APPOINTMENT_DETAILS
import com.example.barbarbookingapp.view.navigation.NavRoutes.APPOINTMENT_LIST
import com.example.barbarbookingapp.view.navigation.NavRoutes.DASHBOARD
import com.example.barbarbookingapp.view.navigation.NavRoutes.LOGIN
import com.example.barbarbookingapp.view.navigation.NavRoutes.SALON_INFORMATION
import com.example.barbarbookingapp.view.navigation.NavRoutes.SELECT_BARBER
import com.example.barbarbookingapp.view.navigation.NavRoutes.SELECT_SERVICE
import com.example.barbarbookingapp.view.navigation.NavRoutes.SELECT_TIME_SLOT
import com.example.barbarbookingapp.view.navigation.NavRoutes.SIGNUP
import com.example.barbarbookingapp.view.navigation.NavRoutes.SPLASH

sealed class Screen(val route: String) {
    object Splash : Screen(SPLASH)
    object Login : Screen(LOGIN)
    object SignUp : Screen(SIGNUP)
    object SelectTimeSlot : Screen(SELECT_TIME_SLOT)
    object SelectService : Screen(SELECT_SERVICE)
    object SelectBarber : Screen(SELECT_BARBER)
    object DashboardScreen : Screen(DASHBOARD)
    object SalonInformation : Screen(SALON_INFORMATION)
    object AppointmentList: Screen(APPOINTMENT_LIST)
    class AppointmentDetails(val appointmentId: Int): Screen("${NavRoutes.APPOINTMENT_DETAILS}/$appointmentId")

}
