package com.example.barbarbookingapp.view.service_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barbarbookingapp.viewmodel.SelectTimeViewModel

@Composable
//@Preview(showBackground = true)
fun SelectTimeSlotScreen(viewModel: SelectTimeViewModel) {
    viewModel.setStartTime(Pair(9, 0))
    TimeSlotGrid(viewModel)
}

@Composable
fun TimeSlotGrid(viewModel: SelectTimeViewModel) {
    var startTime by remember{ mutableStateOf(Pair(0,0)) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 80.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(getSlotsForOneDay()) {curTime->

            val isOccupied = isSlotInRange(curTime, startTime, 60)

            Card(
                modifier = Modifier.padding(10.dp)
                    .clickable {
                        viewModel.setStartTime(curTime)
                        startTime = curTime
                    },
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if(isOccupied) Color.Gray else Color.White)
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

@Composable
@Preview
fun ShowTestScreen() {
//    TimeSlotGrid(SelectTimeViewModel())
}