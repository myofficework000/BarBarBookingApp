package com.example.barbarbookingapp.view.appointment_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.viewmodel.BarberViewModel

@Composable
fun AppointmentDetails(viewModel: BarberViewModel, appointmentId: Int, navController: NavController){
    val services = listOf(
        Service(1, "Haircut", 30, 40.0,""),
        Service(2, "Massage", 60, 100.0,"")
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
        val (dateTimeTitle, dateText, timeText, idText, statusText, barberTitle, barberName, serviceList, billText ) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(idText){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            fontSize = 25.sp,
            text = stringResource(R.string.appointment_id)
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
            text = stringResource(R.string.status)
        )

        Text(
            modifier = Modifier
                .constrainAs(dateTimeTitle) {
                    top.linkTo(statusText.bottom)
                    start.linkTo(parent.start)
                }
                .padding(0.dp, 10.dp),
            fontSize = 25.sp,
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
            text = stringResource(R.string.date)
        )

        Text(
            modifier = Modifier
                .constrainAs(timeText) {
                    top.linkTo(dateText.bottom)
                    start.linkTo(parent.start)
                }
                .padding(0.dp, 5.dp),
            fontSize = 20.sp,
            text = stringResource(R.string.time)
        )

        Text(
            modifier = Modifier
                .constrainAs(barberTitle) {
                    top.linkTo(timeText.bottom)
                    start.linkTo(parent.start)
                }
                .padding(0.dp, 10.dp),
            fontSize = 25.sp,
            text = stringResource(R.string.barber)
        )

        Text(
            modifier = Modifier
                .constrainAs(barberName) {
                    top.linkTo(barberTitle.bottom)
                    start.linkTo(parent.start)
                }
                .padding(0.dp, 5.dp),
            fontSize = 20.sp,
            text = stringResource(R.string.barber_name)
        )

        Column(
            modifier = Modifier
                .constrainAs(serviceList) {
                    top.linkTo(barberName.bottom)
                    start.linkTo(parent.start)
                }
                .padding(0.dp, 10.dp)
        ) {
            services.forEach {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
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
            text = stringResource(R.string.total_Cost)
        )
    }
}