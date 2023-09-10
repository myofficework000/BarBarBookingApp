package com.example.barbarbookingapp.model.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AppointmentWithServices(
    @Embedded
    val appointment: Appointment,
    @Relation(
        entity = Service::class,
        parentColumn = "appointmentId",
        entityColumn = "serviceId",
        associateBy = Junction(AppointmentServiceCrossRef::class)
    )
    val services: List<Service>
)
