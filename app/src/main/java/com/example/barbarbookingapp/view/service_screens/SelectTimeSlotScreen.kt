package com.example.barbarbookingapp.view.service_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barbarbookingapp.viewmodel.AppointmentViewModel
import com.example.barbarbookingapp.viewmodel.BarberViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
//@Preview(showBackground = true)
fun SelectTimeSlotScreen(viewModel: BarberViewModel) {
    viewModel.setStartTime(Pair(9, 0))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopDateSelectBar()
        TimeSlotGrid(viewModel)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(text = "Next")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TopDateSelectBar() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var date by remember {
        mutableStateOf(Date())
    }
    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
        Row(
            modifier = Modifier.menuAnchor()
                .padding(10.dp)
        ) {
            Text(text = formatDate(date))
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
        }

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            getFutureSevenDays().forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = formatDate(it))
                    },
                    onClick = {
                        date = it
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun TimeSlotGrid(viewModel: BarberViewModel) {
    var startTime by remember { mutableStateOf(Pair(0, 0)) }
    var appointments = viewModel.appointmentWithServices.value
    val services = viewModel.selectedServices.observeAsState()
    val serviceDuration = services.value?.sumOf { it.duration }?:15
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 80.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(getSlotsForOneDay()) { curTime ->

            val isOccupied = isSlotInRange(curTime, startTime, serviceDuration)

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        viewModel.setStartTime(curTime)
                        startTime = curTime
                    },
                shape = RectangleShape,
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if (isOccupied) Color.Gray else Color.White)
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
    return currentPoint >= startPoint && currentPoint <= (startPoint + serviceDuration)
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

private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("EEEE,dd MMM yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

@Composable
@Preview
fun ShowTestScreen() {
//    TimeSlotGrid(SelectTimeViewModel())
}