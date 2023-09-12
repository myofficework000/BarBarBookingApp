package com.example.barbarbookingapp.view.intro_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.view.navigation.NavRoutes

@Composable
fun DashboardScreen(navController: NavController) {
    ConstraintLayout(Modifier.fillMaxSize()) {

        val (fieldFirstColumn, fieldSecondColumn) = createRefs()

        Column(Modifier.constrainAs(fieldFirstColumn){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
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

        Column(Modifier.constrainAs(fieldSecondColumn){
            top.linkTo(fieldFirstColumn.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Row {
                Column(Modifier.align(CenterVertically).padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.baseline_calendar_month_24),
                        contentDescription = "img",
                        modifier = Modifier.align(CenterHorizontally).clickable {
                            /*navController.navigate(
                                NavRoutes.NAVIGATE TO APPOINTEMTS)*/
                            })
                    Text(text = "Appointments", Modifier.align(CenterHorizontally))
                }
                Column(Modifier.align(CenterVertically).padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.baseline_cleaning_services_24),
                        contentDescription = "img",
                        modifier = Modifier.align(CenterHorizontally).clickable {
                            navController.navigate(
                                NavRoutes.SELECT_SERVICE
                            )})
                    Text(text = "Services", Modifier.align(CenterHorizontally))
                }
                Column(Modifier.align(CenterVertically).padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.baseline_access_time_24),
                        contentDescription = "img",
                        modifier = Modifier.align(CenterHorizontally))
                    Text(text = "Hours", Modifier.align(CenterHorizontally))
                }
                Column(Modifier.align(CenterVertically).padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.baseline_more_horiz_24),
                        contentDescription = "img",
                        modifier = Modifier.align(CenterHorizontally))
                    Text(text = "More", modifier = Modifier.align(CenterHorizontally))
                }
            }
        }
    }
}