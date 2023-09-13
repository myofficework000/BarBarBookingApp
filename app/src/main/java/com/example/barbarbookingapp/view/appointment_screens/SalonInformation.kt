package com.example.barbarbookingapp.view.appointment_screens

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.model.dto.Salon
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

@Composable
fun SalonInformation(navController: NavController) {
    val context = LocalContext.current
    val salon = Salon(
        1,
        "Good Barber",
        "475-495-2134",
        "salon@mail.com",
        "1621 E Hennepin Ave b20, Minneapolis, MN 55414",
        "",
        5)
    val location = getLatLngFromAddress(context=context, address = salon.address)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(20.dp, 10.dp),
            fontSize = 20.sp,
            text = "Salon Name"
        )
        Text(
            modifier = Modifier.padding(20.dp, 5.dp),
            fontSize = 20.sp,
            color = Color.Gray,
            text = salon.name
        )
        Divider(
            modifier = Modifier.padding(0.dp, 5.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
        Text(
            modifier = Modifier.padding(20.dp, 10.dp),
            fontSize = 20.sp,
            text = "Contact Number"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = 20.sp,
                color = Color.Gray,
                text = salon.phone
            )
            IconButton(onClick = { dialPhoneNumber(context, salon.phone) }) {
                Image(painter = painterResource(id = R.drawable.ic_call), contentDescription = null)
            }
        }

        Divider(
            modifier = Modifier.padding(0.dp, 5.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
        Text(
            modifier = Modifier.padding(20.dp, 10.dp),
            fontSize = 20.sp,
            text = "Email"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = 20.sp,
                color = Color.Gray,
                text = salon.email
            )
            IconButton(onClick = { sendEmail(context, salon.email) }) {
                Image(painter = painterResource(id = R.drawable.ic_email), contentDescription = null)
            }
        }
        Divider(
            modifier = Modifier.padding(0.dp, 5.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
        Text(
            modifier = Modifier.padding(20.dp, 10.dp),
            fontSize = 20.sp,
            text = "Address"
        )
        Text(
            modifier = Modifier.padding(20.dp, 5.dp),
            fontSize = 20.sp,
            color = Color.Gray,
            text = salon.address
        )
        Divider(
            modifier = Modifier.padding(0.dp, 5.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        if (location != null) {
            MapView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                location = location
            ) { googleMap ->
                googleMap.addMarker(MarkerOptions().position(location).title(salon.name))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
            }
        }
    }
}

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    location: LatLng,
    onMapReady: (GoogleMap) -> Unit
) {
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(
        modifier = modifier,
        factory = { mapView }
    ) {
        mapView.getMapAsync { googleMap ->
            onMapReady(googleMap)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    val lifecycleObserver = rememberUpdatedState(LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
        }
    })

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver.value)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver.value)
        }
    }
    return mapView
}

fun getLatLngFromAddress(context: Context, address: String): LatLng? {
    return try {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(address, 1)
        if (addresses?.isNotEmpty() == true) {
            val location = addresses?.get(0)
            location?.let { LatLng(it.latitude, location.longitude) }
        } else {
            null
        }
    } catch (e: IOException) {
        null
    }
}

fun dialPhoneNumber(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

fun sendEmail(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}


