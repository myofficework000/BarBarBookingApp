package com.example.barbarbookingapp.view.appointment_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.barbarbookingapp.viewmodel.BarberViewModel

@Composable
fun UserAppointmentsScreen(viewModel: BarberViewModel, userId: Int) {

    LaunchedEffect(key1 = true) {
        viewModel.selectedUserId(userId)
    }

    val user by viewModel.selectedUser.observeAsState(initial = null)
    val appointments by viewModel.appointmentsForUser.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (user != null) {
            Text(text = "User Info:")
            Text(text = "First Name: ${user!!.firstName}")
            Text(text = "Last Name: ${user!!.lastName}")
            Text(text = "Email: ${user!!.email}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Appointments:")
            LazyColumn {
                items(appointments) { appointment ->
                    Text(text = "Appointment ID: ${appointment.appointmentId}")
                    Text(text = "Date: ${appointment.appointmentDate}")
                    Text(text = "Time: ${appointment.appointmentTime}")
                    Text(text = "Status: ${appointment.status.name}")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Text(text = "Loading user information...")
        }
    }
}
