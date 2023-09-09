package com.example.barbarbookingapp.view.intro_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.barbarbookingapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController) {

    var userFirstName by remember { mutableStateOf("") }
    var userLastName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userRepeatPassword by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {

        Column(
            Modifier
                .padding(20.dp)
                .align(Alignment.Center)
        ) {

            Text(
                text = stringResource(R.string.join_and_find_best_barbers),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp, bottom = 40.dp),
                fontSize = 23.sp
            )

            TextField(
                value = userFirstName,
                onValueChange = {userFirstName = it},
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = { Text(text = "First Name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(R.string.email))
                }
            )

            TextField(
                value = userLastName,
                onValueChange = {userLastName = it},
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = { Text(text = stringResource(R.string.last_name)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(R.string.email))
                }
            )

            TextField(
                value = userEmail,
                onValueChange = {userEmail = it},
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = { Text(text = stringResource(R.string.email)) },
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
                label = { Text(text = stringResource(R.string.password)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = stringResource(R.string.pass) )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            TextField(
                value = userRepeatPassword,
                onValueChange = {userRepeatPassword = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = { Text(text = stringResource(R.string.confirm_password)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = stringResource(R.string.pass) )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            Button(
                onClick = {},
                Modifier
                    .width(200.dp)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)

            ) {
                Text(text = stringResource(R.string.sign_up))
            }
        }
    }
}