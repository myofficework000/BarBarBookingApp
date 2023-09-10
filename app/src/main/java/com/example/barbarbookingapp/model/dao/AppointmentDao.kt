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

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: Service): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointmentServiceCrossRef(crossRef: AppointmentServiceCrossRef)

    @Transaction
    @Query("SELECT * FROM appointments WHERE appointmentId = :appointmentId")
    suspend fun getAppointmentWithServices(appointmentId: Int): AppointmentWithServices

    @Transaction
    @Query("SELECT * FROM appointments WHERE userId = :userId")
    suspend fun getAppointmentsForUser(userId: Int): List<Appointment>
}