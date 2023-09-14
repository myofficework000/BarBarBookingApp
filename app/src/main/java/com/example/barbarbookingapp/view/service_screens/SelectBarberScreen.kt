package com.example.barbarbookingapp.view.service_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.barbarbookingapp.view.navigation.NavRoutes
import com.example.barbarbookingapp.view.theme.Purple40
import com.example.barbarbookingapp.view.theme.Purple80
import com.example.barbarbookingapp.view.theme.PurpleGrey40
import com.example.barbarbookingapp.viewmodel.BarberViewModel

@Composable
fun SelectBarberScreen(viewModel: BarberViewModel, navController: NavController) {
    val context = LocalContext.current
    val allBarbers = viewModel.barbers.observeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Select your favorite barber first!",
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = PurpleGrey40
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        LazyColumn(Modifier.fillMaxWidth()){
            allBarbers.value?.let {
                items(it){
                    Card (
                        Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
                            .clickable {
                                viewModel.selectedBarberId(it.barberId)
                                navController.navigate(NavRoutes.SELECT_SERVICE)
                            }
                    ) {
                        Column(Modifier.padding(10.dp)) {
                            val resourceId = context.resources.getIdentifier(it.image, "drawable", context.packageName)
                            Image(painter = painterResource(id = resourceId),
                                contentDescription = it.firstName,
                                modifier = Modifier
                                    .size(200.dp, 200.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = "${it.firstName} ${it.lastName}",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(8.dp),
                                style = TextStyle(
                                    fontStyle = FontStyle.Normal,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.padding(10.dp))

                            Text(text = "Experience:",
                                Modifier.padding(10.dp),
                                style = TextStyle(
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 15.sp,
                                    color = Purple40
                                ))
                            Text(text = it.experience,modifier = Modifier.align(Alignment.CenterHorizontally).padding(10.dp))

                        }
                    }
                }
            }
        }
    }

}