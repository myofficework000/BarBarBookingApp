package com.example.barbarbookingapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.barbarbookingapp.model.dto.Barber
import kotlinx.coroutines.flow.Flow

@Dao
interface BarberDao {
    @Query("SELECT * FROM barbers")
    fun getAllBarbers(): Flow<List<Barber>>

    @Query("SELECT * FROM barbers WHERE  barberId = :barberId")
    fun getBarberById(barberId:Int): Flow<Barber>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBarber(barber: List<Barber>)
}