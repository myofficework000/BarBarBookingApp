package com.example.barbarbookingapp.view.appointment_screens

import android.widget.ScrollView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Scroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.model.dto.Status
import com.example.barbarbookingapp.view.navigation.NavRoutes
import com.example.barbarbookingapp.view.navigation.NavRoutes.SALON_INFORMATION
import com.example.barbarbookingapp.view.theme.NextBlue
import com.example.barbarbookingapp.viewmodel.BarberViewModel

@Composable
fun AppointmentDetails(viewModel: BarberViewModel, appointmentId: Int, navController: NavController){

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        viewModel.selectedAppointmentId(appointmentId)
    }
    val appointmentWithServices by viewModel.selectedAppointmentWithServices.observeAsState(null)
    val barber by viewModel.selectedBarber.observeAsState(null)
    appointmentWithServices?.let { appointmentDetails ->
        LaunchedEffect(key1 = appointmentDetails.appointment.barberId) {
            viewModel.selectedBarberId(appointmentDetails.appointment.barberId)
        }
    }
    val services = appointmentWithServices?.services

    ConstraintLayout(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(20.dp)) {
        val (dateTimeTitle, dateText, timeText, idText, statusText, cancelButton, barberTitle, barberCard, storeLink, serviceTitle, serviceList, billText ,buttonConfirm) = createRefs()
        appointmentWithServices?.let {
            Text(
                modifier = Modifier
                    .constrainAs(idText){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                fontSize = 25.sp,
                text = "Appointment #${appointmentWithServices!!.appointment.appointmentId}"
            )

            Text(
                modifier = Modifier
                    .constrainAs(statusText) {
                        top.linkTo(idText.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(0.dp, 10.dp),
                fontSize = 25.sp,
                color = Color.Red,
                text = appointmentWithServices!!.appointment.status.toString()
            )
            
            if (appointmentWithServices!!.appointment.status == Status.SCHEDULED) {
                TextButton(
                    modifier = Modifier
                        .constrainAs(cancelButton) {
                            end.linkTo(parent.end, 10.dp)
                            top.linkTo(idText.bottom)
                        },
                    onClick = { showDialog = true }
                ) {
                    Text(text = "Cancel Appointment")
                }
            }
            
            if (showDialog) {
                AlertDialog(
                    title = { Text(text = "Are you sure you want to cancel this appointment?") },
                    onDismissRequest = { showDialog = false }, 
                    confirmButton = { 
                        TextButton(onClick = { 
                            viewModel.updateAppointmentStatus(appointmentId, Status.CANCELED)
                            showDialog = false
                        }) {
                            Text(text = "Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(text = "No")
                        }
                    }
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(dateTimeTitle) {
                        top.linkTo(statusText.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(0.dp, 10.dp),
                fontSize = 25.sp,
                color = Color.Gray,
                text = stringResource(R.string.date_time)
            )

            Text(
                modifier = Modifier
                    .constrainAs(dateText) {
                        top.linkTo(dateTimeTitle.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(0.dp, 5.dp),
                fontSize = 20.sp,
                text = appointmentWithServices!!.appointment.appointmentDate
            )

            Text(
                modifier = Modifier
                    .constrainAs(timeText) {
                        top.linkTo(dateTimeTitle.bottom)
                        start.linkTo(dateText.end, 10.dp)
                    }
                    .padding(0.dp, 5.dp),
                fontSize = 20.sp,
                text = appointmentWithServices!!.appointment.appointmentTime
            )

            Text(
                modifier = Modifier
                    .constrainAs(barberTitle) {
                        top.linkTo(timeText.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(0.dp, 10.dp),
                fontSize = 25.sp,
                color = Color.Gray,
                text = stringResource(R.string.barber)
            )
            barber?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(barberCard) {
                            top.linkTo(barberTitle.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(0.dp, 5.dp),
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
                        Text(
                            fontSize = 20.sp,
                            text = "${barber!!.firstName} ${barber!!.lastName}"
                        )
                        val resourceId = context.resources.getIdentifier(barber!!.image, "drawable", context.packageName)
                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(id = resourceId),
                            contentDescription = "barber"
                        )
                    }
                }
            }

            TextButton(
                modifier = Modifier
                    .constrainAs(storeLink){
                        end.linkTo(parent.end)
                        top.linkTo(barberCard.bottom)
                    },
                onClick = { navController.navigate(SALON_INFORMATION) }
            ) {
                Text(text = "View Store Details")
            }

            Text(
                modifier = Modifier
                    .constrainAs(serviceTitle) {
                        top.linkTo(storeLink.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(0.dp, 10.dp),
                fontSize = 25.sp,
                color = Color.Gray,
                text = stringResource(R.string.services)
            )

            Column(
                modifier = Modifier
                    .constrainAs(serviceList) {
                        top.linkTo(serviceTitle.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(0.dp, 10.dp)
            ) {
                services?.forEach {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp, 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val resourceId = context.resources.getIdentifier(it.image, "drawable", context.packageName)
                            Image(
                                modifier = Modifier.size(80.dp),
                                painter = painterResource(id = resourceId),
                                contentDescription = "barber"
                            )
                            Spacer(modifier = Modifier.size(30.dp))
                            Column {
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = it.name
                                )
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = "${it.duration.toString()} Minutes"
                                )
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = "$ ${it.price.toString()}"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
            Text(
                modifier = Modifier
                    .constrainAs(billText) {
                        top.linkTo(serviceList.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(0.dp, 10.dp),
                fontSize = 25.sp,
                text = "Total Cost: $ ${appointmentWithServices!!.appointment.serviceCharge.toString()}"
            )
            Button(
                onClick = { navController.navigate(NavRoutes.DASHBOARD) },
                colors = ButtonDefaults.buttonColors(NextBlue),
                modifier = Modifier.constrainAs(buttonConfirm){
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Text(text = "Confirm")
            }
        }


    }
}