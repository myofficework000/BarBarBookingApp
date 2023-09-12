package com.example.barbarbookingapp.view.service_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.view.navigation.NavRoutes
import com.example.barbarbookingapp.viewmodel.BarberViewModel

@Composable
fun SelectServiceScreen(viewModel: BarberViewModel, navController: NavController) {

    val serviceTitle by remember { mutableStateOf("Haircuts") }
    val allServices = viewModel.allServices.observeAsState()
    val selectedServices = mutableListOf<Service>()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (title, choices, buttonGroup) = createRefs()

        Text(text = serviceTitle, fontSize = 30.sp, modifier = Modifier.constrainAs(title) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(choices) {
                    top.linkTo(title.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttonGroup.top, 10.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        ) {
            items(allServices.value ?: emptyList()) {
                ServiceItemCard(serviceItem = it, selectedServices)
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }

        Box(modifier = Modifier.constrainAs(buttonGroup) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }) {
            Row {
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(Color.Red),

                    ) {
                    Text(text = "Change Barber")
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        viewModel.setSelectedServices(selectedServices)
                        navController.navigate(NavRoutes.SELECT_TIME_SLOT)
                    },
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(text = "next")
                }
            }
        }
    }

}

@Composable
fun ServiceItemCard(serviceItem: Service, selectedServices: MutableList<Service>) {
    val selectedOption = remember { mutableStateOf("unselected") }
    Card(
        Modifier
            .padding(3.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
    ) {
        ConstraintLayout(
            Modifier
                .background(Color.White)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            val (itemImage, itemTitle, itemSelector, itemDetailBox) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = serviceItem.name,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .constrainAs(itemImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )

            RadioButton(
                selected = selectedOption.value == "selected",
                onClick = {
                    if (selectedOption.value == "selected") {
                        selectedOption.value = "unselected"
                        selectedServices.remove(serviceItem)
                    } else {
                        selectedOption.value = "selected"
                        selectedServices.add(serviceItem)
                    }
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black,
                    unselectedColor = Color.Gray
                ),
                modifier = Modifier.constrainAs(itemSelector) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
            )
            Text(text = serviceItem.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(itemTitle) {
                        top.linkTo(parent.top)
                        start.linkTo(itemImage.end)
                    })
            Row(
                modifier = Modifier.constrainAs(itemDetailBox) {
                    top.linkTo(itemTitle.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(itemImage.end)
                    end.linkTo(itemSelector.start)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_av_timer_24),
                    contentDescription = "clock",
                    Modifier.size(30.dp)
                )
                Text(text = "${serviceItem.duration} minutes")
                Spacer(modifier = Modifier.padding(20.dp))
                Icon(
                    painter = painterResource(id = R.drawable.baseline_attach_money_24),
                    contentDescription = "clock",
                    Modifier.size(30.dp)
                )
                Text(text = "${serviceItem.price}")
            }
        }
    }
}