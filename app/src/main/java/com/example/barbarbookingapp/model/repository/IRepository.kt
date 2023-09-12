package com.example.barbarbookingapp.model.repository

import com.example.barbarbookingapp.model.ResultState
import com.example.barbarbookingapp.model.dto.Appointment
import com.example.barbarbookingapp.model.dto.AppointmentServiceCrossRef
import com.example.barbarbookingapp.model.dto.AppointmentWithServices
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.model.dto.Status
import com.example.barbarbookingapp.model.dto.User
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun getAllUsers(): Flow<List<User>>
    fun getUserById(userId: Int): Flow<User>
    suspend fun insertUsers(users: List<User>)

    fun getAllBarbers(): Flow<List<Barber>>
    fun getBarberById(barberId: Int): Flow<Barber>
    suspend fun insertBarber(barbers: List<Barber>)

    fun getAllServices(): Flow<List<Service>>

    fun getServiceById(serviceId:Int): Flow<Service>

    suspend fun insertService(service: List<Service>)

    fun getServicePrices(serviceIds: List<Int>): Flow<List<Double>>

    suspend fun insertAppointment(appointment: Appointment): Long

    suspend fun insertAppointmentServiceCrossRef(crossRef: AppointmentServiceCrossRef)

    fun getAppointmentWithServices(appointmentId: Int): Flow<AppointmentWithServices>

    fun getAppointmentsForUser(userId: Int): Flow<List<Appointment>>

    suspend fun updateAppointmentStatus(appointmentId: Int, status: Status)

    fun getAppointmentDuration(appointmentId: Int): Flow<Int?>

}
