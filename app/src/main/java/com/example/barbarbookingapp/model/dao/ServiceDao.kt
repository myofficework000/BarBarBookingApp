package com.example.barbarbookingapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.barbarbookingapp.model.dto.Service

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services")
    suspend fun getAllServices():List<Service>

    @Query("SELECT * FROM services WHERE  serviceId = :serviceId")
    suspend fun getService(serviceId:Int): Service

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: List<Service>)

    @Query("SELECT price FROM services WHERE serviceId IN (:serviceIds)")
    suspend fun getServicePrices(serviceIds: List<Int>): List<Double>
}