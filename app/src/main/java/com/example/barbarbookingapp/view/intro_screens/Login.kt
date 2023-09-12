package com.example.barbarbookingapp.view.intro_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.view.navigation.NavRoutes.DASHBOARD
import com.example.barbarbookingapp.view.navigation.NavRoutes.SIGNUP

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController){

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {
        
        Column(Modifier
            .padding(20.dp)
        ) {

            Text(
                text = stringResource(R.string.find_my_barber),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 30.dp),
                fontSize = 23.sp
            )

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .align(CenterHorizontally)
            )

            TextField(
                value = userEmail,
                onValueChange = {userEmail = it},
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = { Text(text = stringResource(R.string.email))},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = stringResource(R.string.email))
                }
            )

            TextField(
                value = userPassword,
                onValueChange = {userPassword = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = { Text(text = stringResource(R.string.password))},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = stringResource(R.string.pass) )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )

            ClickableText(
                text = AnnotatedString(stringResource(R.string.forgot_password)),
                onClick = {},
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            Button(
                onClick = {navController.navigate(DASHBOARD)},
                Modifier
                    .width(200.dp)
                    .padding(top = 10.dp)
                    .align(CenterHorizontally)

            ) {
                Text(text = stringResource(R.string.login))
            }

            Spacer(modifier = Modifier.weight(1f))

            ClickableText(
                text = AnnotatedString(stringResource(R.string.don_t_have_account)),
                onClick = { navController.navigate(SIGNUP) },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(CenterHorizontally)
            )
        }
    }
}