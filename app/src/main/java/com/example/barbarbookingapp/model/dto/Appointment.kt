package com.example.barbarbookingapp.model.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "appointments",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Barber::class,
            parentColumns = ["barberId"],
            childColumns = ["barberId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    var appointmentId: Int = 0,
    val userId: Int,
    val barberId: Int,
    val appointmentDate: String,
    val appointmentTime: String,
    var serviceCharge: Double = 0.0,
    val status: Status,
    val paymentMethod: PaymentMethod
)

enum class PaymentMethod{
    BANK, CASH
}
enum class Status {
    SCHEDULED, CANCELED, COMPLETED
}