package com.example.barbarbookingapp.view.intro_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.view.navigation.NavRoutes.LOGIN
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val rawComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_barber)
    )

    Box(Modifier.fillMaxSize()) {
        LottieAnimation(
            composition = rawComposition,
            iterations = 1,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
        )
    }

    LaunchedEffect(key1 = true) {
        delay(5000)
        navController.navigate(LOGIN)
    }
}