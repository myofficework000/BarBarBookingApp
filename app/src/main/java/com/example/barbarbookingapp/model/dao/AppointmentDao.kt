package com.example.barbarbookingapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.barbarbookingapp.model.dto.Appointment
import com.example.barbarbookingapp.model.dto.AppointmentServiceCrossRef
import com.example.barbarbookingapp.model.dto.AppointmentWithServices
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.model.dto.Status
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointmentServiceCrossRef(crossRef: AppointmentServiceCrossRef)

    @Transaction
    @Query("SELECT * FROM appointments WHERE appointmentId = :appointmentId")
    fun getAppointmentWithServices(appointmentId: Int): Flow<AppointmentWithServices>

    @Transaction
    @Query("SELECT * FROM appointments WHERE userId = :userId")
    fun getAppointmentsForUser(userId: Int): Flow<List<Appointment>>

    @Query("UPDATE appointments SET status = :status WHERE appointmentId = :appointmentId")
    suspend fun updateAppointmentStatus(appointmentId: Int, status: Status)
}