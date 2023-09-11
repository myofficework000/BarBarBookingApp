package com.example.barbarbookingapp.view.service_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.ServiceItem

@Composable
@Preview
fun SelectServiceScreen(){

    val serviceTitle by remember{ mutableStateOf("Haircuts") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = serviceTitle, fontSize = 30.sp)
        Spacer(modifier = Modifier.padding(5.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(getServiceItems()){
                ServiceItemCard(serviceItem = it)
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = "Change Barber")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(text = "next")
            }
        }
    }


}

@Composable
fun ServiceItemCard(serviceItem: ServiceItem){
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
                contentDescription = serviceItem.title,
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
                    } else {
                        selectedOption.value = "selected"
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
            Text(text = serviceItem.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(itemTitle) {
                        top.linkTo(parent.top)
                        start.linkTo(itemImage.end)
                    })
            Row(
                modifier = Modifier.constrainAs(itemDetailBox){
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

@Composable
fun testCard(){
    ServiceItemCard(serviceItem = ServiceItem(
        "",
        "test",
        4f,
        15f
    ))
}

private fun getServiceItems():List<ServiceItem>{
    return listOf(
        ServiceItem("","service1",5f,15f),
        ServiceItem("","service2",10f,15f),
        ServiceItem("","service3",15f,15f),
        ServiceItem("","service4",20f,15f),
        ServiceItem("","service5",10f,15f),
    )
}