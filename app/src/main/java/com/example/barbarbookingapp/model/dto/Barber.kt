package com.example.barbarbookingapp.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barbers")
data class Barber(
    @PrimaryKey(autoGenerate = true)
    var barberId: Int = 0,
    val firstName: String,
    val lastName: String,
    val experience: String,
    val rating: Int
)
