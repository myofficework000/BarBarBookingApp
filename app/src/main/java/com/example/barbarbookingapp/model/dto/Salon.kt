package com.example.barbarbookingapp.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salons")
data class Salon(
    @PrimaryKey(autoGenerate = true)
    var salonId: Int = 0,
    val name: String,
    val phone: String,
    val email: String,
    val address: String,
    val image: String,
    val rating: Int
)
