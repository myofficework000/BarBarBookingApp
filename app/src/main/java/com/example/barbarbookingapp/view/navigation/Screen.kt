package com.example.barbarbookingapp.view.navigation

import com.example.barbarbookingapp.view.navigation.NavRoutes.LOGIN
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
}
