package com.example.barbarbookingapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.barbarbookingapp.model.dto.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services")
    fun getAllServices(): Flow<List<Service>>

    @Query("SELECT * FROM services WHERE  serviceId = :serviceId")
    fun getServiceById(serviceId:Int): Flow<Service>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: List<Service>)

    @Query("SELECT price FROM services WHERE serviceId IN (:serviceIds)")
    fun getServicePrices(serviceIds: List<Int>): Flow<List<Double>>


}