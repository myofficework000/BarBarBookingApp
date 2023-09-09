package com.example.barbarbookingapp.model.dto


enum class PaymentMethod{
    BANK, CASH
}

enum class Status {
    SCHEDULED, CANCELED, COMPLETED
}
data class Appointment(
    val appointmentId: Int,
    val userId: Int,
    val barberId: Int,
    val services: List<Service>,
    val appointmentDate: String,
    val appointmentTime: String,
    val serviceCharge: Double,
    val status: Status,
    val paymentMode: PaymentMethod
)
