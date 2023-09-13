package com.example.barbarbookingapp.view.service_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barbarbookingapp.R
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.view.theme.NextBlue

@Composable
fun ServiceDetailDialog(service: Service, onDialogClose: OnDialogClose) {
    AlertDialog(
        onDismissRequest = {
            onDialogClose.closeDialog(true)
        },
        title = {
            Text(text = service.name)

        },
        text = {
            Column {
                Divider()

                Text(text = "Duration: ${service.duration} minutes")
                Spacer(modifier = Modifier.padding(5.dp))

                Text(text = "Price: $ ${service.price}")
                Spacer(modifier = Modifier.padding(20.dp))

                Divider()
                Text(
                    text = "Description:",
                    style = TextStyle(fontSize = 20.sp, fontStyle = FontStyle.Italic)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Example service description: Enjoy this Service with an optimal price!",
                    style = TextStyle(fontSize = 10.sp, fontStyle = FontStyle.Normal)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDialogClose.closeDialog(true)
                },
                colors = ButtonDefaults.buttonColors(NextBlue)
            ) {
                Text(text = "Close")
            }
        },
        icon = {
            Image(
                painter = painterResource(id = R.drawable.barber1),
                contentDescription = "servicePicture"
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDialog() {
    ServiceDetailDialog(service = Service(
        name = "Service1",
        duration = 60,
        price = 100.0,
        image = ""
    ), object : OnDialogClose {
        override fun closeDialog(close: Boolean) {}
    }
    )
}

interface OnDialogClose {
    fun closeDialog(close: Boolean)
}