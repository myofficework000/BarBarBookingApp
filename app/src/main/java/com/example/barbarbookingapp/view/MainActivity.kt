package com.example.barbarbookingapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barbarbookingapp.view.appointment_screens.UserAppointmentsScreen
import com.example.barbarbookingapp.view.navigation.MyApp
import com.example.barbarbookingapp.view.theme.BarbarBookingAPPTheme
import com.example.barbarbookingapp.viewmodel.BarberViewModel
import com.example.barbarbookingapp.viewmodel.InsertDataViewModel
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
                    //MyApp()

                    // Use this view model to initialize data :)
                    //var insertDataViewModel: InsertDataViewModel = hiltViewModel()
                    //insertDataViewModel.initDb()
                    var barberViewModel: BarberViewModel = hiltViewModel()
                    UserAppointmentsScreen(barberViewModel, 1)
                }
            }
        }
    }
}