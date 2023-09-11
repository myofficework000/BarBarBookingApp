package com.example.barbarbookingapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.barbarbookingapp.view.appointment_screens.UserAppointmentsScreen
import com.example.barbarbookingapp.view.intro_screens.Login
import com.example.barbarbookingapp.view.intro_screens.SignUp
import com.example.barbarbookingapp.view.intro_screens.SplashScreen
import com.example.barbarbookingapp.view.navigation.MyApp
import com.example.barbarbookingapp.view.navigation.Screen
import com.example.barbarbookingapp.view.service_screens.SelectServiceScreen
import com.example.barbarbookingapp.view.service_screens.SelectTimeSlotScreen
import com.example.barbarbookingapp.view.theme.BarbarBookingAPPTheme
import com.example.barbarbookingapp.viewmodel.AppointmentViewModel
import com.example.barbarbookingapp.viewmodel.BarberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarbarBookingAPPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}