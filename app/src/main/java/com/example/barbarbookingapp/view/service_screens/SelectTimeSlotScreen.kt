package com.example.barbarbookingapp.view.service_screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.barbarbookingapp.model.SharedPref
import com.example.barbarbookingapp.model.Utils.formatDateFromString
import com.example.barbarbookingapp.model.Utils.formatDateIntoString
import com.example.barbarbookingapp.model.Utils.formatIntoTime
import com.example.barbarbookingapp.model.Utils.formatTimeIntoString
import com.example.barbarbookingapp.model.Utils.isDate
import com.example.barbarbookingapp.model.Utils.makeToast
import com.example.barbarbookingapp.model.dto.Appointment
import com.example.barbarbookingapp.model.dto.AppointmentServiceCrossRef
import com.example.barbarbookingapp.model.dto.PaymentMethod
import com.example.barbarbookingapp.model.dto.Status
import com.example.barbarbookingapp.view.navigation.NavRoutes
import com.example.barbarbookingapp.view.theme.CancelRed
import com.example.barbarbookingapp.view.theme.NextBlue
import com.example.barbarbookingapp.view.theme.Purple80
import com.example.barbarbookingapp.view.theme.PurpleGrey80
import com.example.barbarbookingapp.viewmodel.BarberViewModel
import java.util.Calendar
import java.util.Date
var i = 0
@Composable
fun SelectTimeSlotScreen(viewModel: BarberViewModel, navController: NavController) {
    viewModel.setStartTime(Pair(9, 0))
    val context = LocalContext.current
    val sharedPref = SharedPref.getSecuredSharedPreferences(context)
    val userId = sharedPref.getInt("user_id", 0)
    val selectedDate = viewModel.selectedDate.observeAsState()
    val selectedTime = viewModel.selectedStartTime.observeAsState()
    val selectedServices = viewModel.selectedServices.observeAsState()
    val scheduledAppointmentId = viewModel.appointmentId.observeAsState()
    //navController.navigate(NavRoutes.APPOINTMENT_DETAILS
    LaunchedEffect(scheduledAppointmentId.value){
        scheduledAppointmentId.value?.apply {
            val crossRef =
                selectedServices.value!!.map { AppointmentServiceCrossRef(this.toInt(), it.serviceId) }
            viewModel.insertAppointmentServiceCrossRef(crossRef)
            Log.i("check","${i++}")
            navController.navigate("${NavRoutes.APPOINTMENT_DETAILS}/$this")
        }
    }


    viewModel.fetchHistoryAppointmentForUserFromUI(userId)

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (dateBar, timeSlotGrid, buttonGroup) = createRefs()

        TopDateSelectBar(viewModel = viewModel, modifier = Modifier.constrainAs(dateBar) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        TimeSlotGrid(
            viewModel = viewModel,
            modifier = Modifier.constrainAs(timeSlotGrid) {
                top.linkTo(dateBar.bottom, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(buttonGroup.top)
                height = Dimension.fillToConstraints
            })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(buttonGroup) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(CancelRed)
            ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    val barberId = 1
                    val appointmentDate = selectedDate.value?.formatDateIntoString("yyyy-MM-dd")
                    val startTime = selectedTime.value?.formatTimeIntoString()
                    val serviceCharge = selectedServices.value?.sumOf { it.price }
                    val paymentMethod = PaymentMethod.CASH
                    val status = Status.SCHEDULED
                    if (appointmentDate != null && startTime != null && serviceCharge != null) {
                        viewModel.insertAppointment(
                            Appointment(
                                userId = userId,
                                barberId = barberId,
                                appointmentDate = appointmentDate,
                                appointmentTime = startTime,
                                paymentMethod = paymentMethod,
                                status = status,
                                serviceCharge = serviceCharge
                            )
                        )

                    } else {
                        makeToast(
                            context,
                            "System is scheduling for your appointment, please wait a few seconds"
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(NextBlue)
            ) {
                Text(text = "Next")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopDateSelectBar(viewModel: BarberViewModel, modifier: Modifier) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var date by remember {
        mutableStateOf(Date())
    }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = modifier
    ) {
        Row(
            modifier = modifier
                .menuAnchor()
                .padding(10.dp)
        ) {
            Text(text = date.formatDateIntoString("EEEE,dd MMM yyyy"))
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
        }

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            getFutureSevenDays().forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.formatDateIntoString("EEEE,dd MMM yyyy"))
                    },
                    onClick = {
                        date = it
                        viewModel.setSelectedDate(date)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun TimeSlotGrid(viewModel: BarberViewModel, modifier: Modifier) {
    var startTime by remember { mutableStateOf(Pair(0, 0)) }
    val curDayOccupation by remember { mutableStateOf(HashMap<Pair<Int, Int>, Int>()) }
    val selectedDate = viewModel.selectedDate.observeAsState()
    val appointments = viewModel.allAppointments.observeAsState()

    selectedDate.value?.apply {
        curDayOccupation.clear()
        appointments.value?.let { appointmentWithServices ->
            for (appointmentWithService in appointmentWithServices) {
                val historyDate =
                    appointmentWithService.appointment.appointmentDate.formatDateFromString("yyyy-MM-dd")
                historyDate!!.let { date ->
                    if (date.isDate(this)) {
                        val totalDuration = appointmentWithService.services.sumOf { it.duration }
                        val appointmentTime =
                            appointmentWithService.appointment.appointmentTime.formatIntoTime()
                        curDayOccupation[appointmentTime] = totalDuration
                    }
                }
            }
        }
    }

//    selectedDate.takeIf {  }

    val services = viewModel.selectedServices.observeAsState()
    val serviceDuration = services.value?.sumOf { it.duration } ?: 15
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 80.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(getSlotsForOneDay()) { curTime ->

            val isOccupied = isSlotInRange(curTime, startTime, serviceDuration)
            val isBooked = isSlotBooked(curTime, curDayOccupation)
            Card(
                modifier = modifier
                    .padding(5.dp)
                    .size(100.dp, 60.dp)
                    .clickable {
                        viewModel.setStartTime(curTime)
                        startTime = curTime
                    },
                shape = RectangleShape,
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            if (isBooked) PurpleGrey80
                            else if (isOccupied) Purple80
                            else Color.White
                        )
                ) {
                    val timeString = curTime.formatTimeIntoString()
                    Text(text = timeString)
                }

            }
        }
    }
}

private fun getSlotsForOneDay(): List<Pair<Int, Int>> {
    val duration = 15

    val startHour = 9
    val endHour = 18
    val result = mutableListOf<Pair<Int, Int>>()
    for (hour in startHour until endHour) {
        for (minutes in 0..59 step duration) {
            result.add(hour to minutes)
        }
    }
    return result
}

private fun isSlotInRange(
    timeSlot: Pair<Int, Int>,
    startTime: Pair<Int, Int>,
    serviceDuration: Int
): Boolean {
    val startPoint = startTime.first * 60 + startTime.second
    val currentPoint = timeSlot.first * 60 + timeSlot.second
    return currentPoint >= startPoint && currentPoint < (startPoint + serviceDuration)
}

private fun isSlotBooked(
    curTime: Pair<Int, Int>,
    curDayOccupation: HashMap<Pair<Int, Int>, Int>
): Boolean {
    curDayOccupation.forEach { (key, value) ->
        if (isSlotInRange(curTime, key, value)) return true
    }
    return false
}

private fun getFutureSevenDays(): List<Date> {
    val calendar = Calendar.getInstance()
    val dateList = mutableListOf<Date>()

    for (i in 0 until 7) {
        dateList.add(calendar.time)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dateList
}
