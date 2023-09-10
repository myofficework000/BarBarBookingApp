package com.example.barbarbookingapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.barbarbookingapp.model.dto.Barber

@Dao
interface BarberDao {
    @Query("SELECT * FROM barbers")
    suspend fun getAllBarber():List<Barber>

    @Query("SELECT * FROM barbers WHERE  barberId = :barberId")
    suspend fun getBarber(barberId:Int): Barber

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBarber(barber: List<Barber>)
}