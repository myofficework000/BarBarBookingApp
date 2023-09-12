package com.example.barbarbookingapp.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class Service(
    @PrimaryKey(autoGenerate = true)
    var serviceId: Int = 0,
    val name: String,
    val duration: Int,
    val price: Double,
    val image: String
)
