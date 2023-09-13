package com.example.barbarbookingapp.view.service_screens

import android.annotation.SuppressLint
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
import com.example.barbarbookingapp.model.Utils.isDate
import com.example.barbarbookingapp.view.navigation.NavRoutes
import com.example.barbarbookingapp.view.theme.CancelRed
import com.example.barbarbookingapp.view.theme.NextBlue
import com.example.barbarbookingapp.view.theme.Purple80
import com.example.barbarbookingapp.view.theme.PurpleGrey80
import com.example.barbarbookingapp.viewmodel.BarberViewModel
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import java.util.Date

@Composable
fun SelectTimeSlotScreen(viewModel: BarberViewModel, navController: NavController) {
    viewModel.setStartTime(Pair(9, 0))
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (dateBar, timeSlotGrid, buttonGroup) = createRefs()

        TopDateSelectBar(viewModel = viewModel, modifier = Modifier.constrainAs(dateBar) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        TimeSlotGrid(viewModel = viewModel, modifier = Modifier.constrainAs(timeSlotGrid) {
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
                    //save appointment into database
                    //navController.navigate(NavRoutes.APPOINTMENT_DETAILS)
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
            Text(text = date.formatDateIntoString())
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
        }

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            getFutureSevenDays().forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.formatDateIntoString())
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
    val context = LocalContext.current
    var startTime by remember { mutableStateOf(Pair(0, 0)) }
    val curDayOccupation by remember { mutableStateOf(HashMap<Pair<Int, Int>, Int>()) }
    var selectedDate = viewModel.selectedDate.observeAsState()
    LaunchedEffect(key1 = true) {
        val sharedPref = SharedPref.getSecuredSharedPreferences(context)
        val userId = sharedPref.getInt("user_id",0)
        viewModel.fetchHistoryAppointmentForUser(userId)
    }
    val appointments = viewModel.allAppointments.observeAsState()

    appointments.value?.let { appointmentWithServices ->
        for (appointmentWithService in appointmentWithServices) {
            val historyDate =
                appointmentWithService.appointment.appointmentDate.formatDateFromString()
            historyDate?.let { date ->
                if (date.isDate(selectedDate.value ?: Date())) {
                    val totalDuration = appointmentWithService.services.map { it.duration }.sum()
                    val appointmentTime =
                        appointmentWithService.appointment.appointmentTime.formatIntoTime()
                    curDayOccupation[appointmentTime] = totalDuration
                } else {
                    curDayOccupation.clear()
                }
            }
        }
    }

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
            var isBooked = isSlotBooked(curTime, curDayOccupation)
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
                    var minutes = "${curTime.second}"
                    if (curTime.second / 10 == 0) minutes = "0${curTime.second}"
                    Text(text = "${curTime.first}:${minutes}")
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
