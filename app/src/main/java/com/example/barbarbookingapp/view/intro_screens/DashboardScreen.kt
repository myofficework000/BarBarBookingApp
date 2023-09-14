package com.example.barbarbookingapp.view.intro_screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.view.navigation.NavRoutes
import com.example.barbarbookingapp.viewmodel.BarberViewModel

@Composable
fun DashboardScreen(viewModel: BarberViewModel, navController: NavController) {
    ConstraintLayout(Modifier.fillMaxSize().verticalScroll(
        rememberScrollState()
    )) {

        val context = LocalContext.current
        val allServices = viewModel.allServices.observeAsState()
        val allBarbers = viewModel.barbers.observeAsState()

        val (fieldFirstColumn, fieldSecondColumn, fieldThirdColumn, fieldFourthColumn) = createRefs()

        Column(
            Modifier
                .constrainAs(fieldFirstColumn) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 30.dp)) {
            Row() {
                Image(painter = painterResource(R.drawable.scisors),
                    contentDescription = "Scissors image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .padding(10.dp))

                Column(Modifier.padding(10.dp)) {
                    Text(text = "Welcome!", fontSize = 25.sp)
                    Text(text = "Let's make your hair attractive!", fontSize = 18.sp)
                }
            }
        }

        Column(
            Modifier
                .constrainAs(fieldSecondColumn) {
                    top.linkTo(fieldFirstColumn.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 40.dp)) {
            Row {
                Column(
                    Modifier
                        .align(CenterVertically)
                        .padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.baseline_calendar_month_24),
                        contentDescription = "img",
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .clickable {
                                navController.navigate(
                                    NavRoutes.APPOINTMENT_LIST
                                )
                            })
                    Text(text = "Appointments", Modifier.align(CenterHorizontally))
                }
                Column(
                    Modifier
                        .align(CenterVertically)
                        .padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.baseline_cleaning_services_24),
                        contentDescription = "img",
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .clickable {
                                navController.navigate(
                                    NavRoutes.SELECT_SERVICE
                                )
                            })
                    Text(text = "Services", Modifier.align(CenterHorizontally))
                }
                Column(
                    Modifier
                        .align(CenterVertically)
                        .padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.baseline_access_time_24),
                        contentDescription = "img",
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .clickable {
                                navController.navigate(
                                    NavRoutes.SALON_INFORMATION
                                )
                            })
                    Text(text = "Hours", Modifier.align(CenterHorizontally))
                }
                Column(
                    Modifier
                        .align(CenterVertically)
                        .padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.baseline_more_horiz_24),
                        contentDescription = "img",
                        modifier = Modifier.align(CenterHorizontally))
                    Text(text = "More", modifier = Modifier.align(CenterHorizontally))
                }
            }
        }

        Column(
            Modifier
                .constrainAs(fieldThirdColumn) {
                    top.linkTo(fieldSecondColumn.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 20.dp)
        ) {

            Text(
                text = stringResource(R.string.our_amazing_barbers),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 15.dp),
                fontSize = 26.sp
            )

            LazyRow(Modifier.padding(top = 10.dp)) {
                items(allBarbers.value ?: emptyList()) {
                    BarbersItemCard(barberItem = it, context)
                }
            }

        }
        Column(
            Modifier
                .constrainAs(fieldFourthColumn) {
                    top.linkTo(fieldThirdColumn.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 20.dp)) {

            Text(
                text = stringResource(R.string.our_services),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 15.dp),
                fontSize = 26.sp
            )

            LazyRow(Modifier.padding(top = 10.dp)) {
                items(allServices.value ?: emptyList()) {
                    ServiceItemCard(serviceItem = it, context)
                }
            }

        }
    }
}

@Composable
fun BarbersItemCard(barberItem : Barber, context: Context) {
    Card (
        Modifier
            .padding(3.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
    ) {
        Column(Modifier.padding(10.dp)) {
            val resourceId = context.resources.getIdentifier(barberItem!!.image, "drawable", context.packageName)
            Image(painter = painterResource(id = resourceId),
                contentDescription = barberItem.firstName,
                modifier = Modifier.align(CenterHorizontally)
            )
            
            Text(
                text = "${barberItem.firstName} ${barberItem.lastName}",
                modifier = Modifier.align(CenterHorizontally)
            )
        }
    }    
}

@Composable
fun ServiceItemCard(serviceItem : Service, context: Context) {
    Card (
        Modifier
            .padding(3.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
    ) {
        Column(Modifier.padding(10.dp))  {
            val resourceId = context.resources.getIdentifier(serviceItem!!.image, "drawable", context.packageName)
            Image(painter = painterResource(resourceId),
                contentDescription = serviceItem.name,
                modifier = Modifier.align(CenterHorizontally)
            )

            Text(
                text = serviceItem.name,
                modifier = Modifier.align(CenterHorizontally)
            )
        }
    }
}
