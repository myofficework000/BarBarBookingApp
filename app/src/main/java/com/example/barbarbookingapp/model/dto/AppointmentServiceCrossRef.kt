package com.example.barbarbookingapp.model.dto

import androidx.room.Entity

@Entity(tableName = "appointment_service_cross_ref",
    primaryKeys = ["appointmentId", "serviceId"])
data class AppointmentServiceCrossRef(
    val appointmentId: Int,
    val serviceId: Int
)
