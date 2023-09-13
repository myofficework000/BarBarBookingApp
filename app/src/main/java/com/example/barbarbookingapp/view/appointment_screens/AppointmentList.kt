package com.example.barbarbookingapp.view.appointment_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.view.navigation.Screen
import com.example.barbarbookingapp.viewmodel.BarberViewModel

@Composable
fun AppointmentList(viewModel: BarberViewModel, userId: Int, navController: NavController) {

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
            Text(
                fontSize = 25.sp,
                text = "Customer #${user!!.userId}"
            )
            Text(
                fontSize = 20.sp,
                text = "Name: ${user!!.firstName} ${user!!.lastName}"
            )
            Text(
                fontSize = 20.sp,
                text = "Email: ${user!!.email}"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                fontSize = 25.sp,
                color = Color.Gray,
                text = "Appointments:"
            )
            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn {
                items(appointments) { appointment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp, 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = "Date: ${appointment.appointmentDate}")
                                Text(text = "Time: ${appointment.appointmentTime}")
                                Text(text = "Status: ${appointment.status.name}")
                                Text(text = "Total Cost: $ ${appointment.serviceCharge}")
                            }
                            IconButton(
                                onClick = { navController.navigate(Screen.AppointmentDetails(appointment.appointmentId).route) }
                            ) {
                                Image(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(id = R.drawable.ic_details),
                                    contentDescription = null
                                )
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Text(text = "Loading user information...")
        }
    }
}
