package com.example.barbarbookingapp.model.dto

data class User(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val appointments: List<Appointment>
)
