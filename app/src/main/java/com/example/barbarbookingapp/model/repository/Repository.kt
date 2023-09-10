package com.example.barbarbookingapp.model.repository

import com.example.barbarbookingapp.model.ResultState
import com.example.barbarbookingapp.model.dao.AppointmentDao
import com.example.barbarbookingapp.model.dao.BarberDao
import com.example.barbarbookingapp.model.dao.ServiceDao
import com.example.barbarbookingapp.model.dao.UserDao
import com.example.barbarbookingapp.model.dto.Appointment
import com.example.barbarbookingapp.model.dto.AppointmentServiceCrossRef
import com.example.barbarbookingapp.model.dto.AppointmentWithServices
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.model.dto.Status
import com.example.barbarbookingapp.model.dto.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository
    @Inject constructor(
        private val userDao: UserDao,
        private val barberDao: BarberDao,
        private val serviceDao: ServiceDao,
        private val appointmentDao: AppointmentDao
    ): IRepository {
    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    override fun getUserById(userId: Int): Flow<User> = userDao.getUserById(userId)

    /*
override suspend fun getAllUser(): Flow<ResultState<List<User>>> {
    return flow {
        userDao.getAllUser().apply {
            this.let {
                emit(ResultState.Success(it))
            }.runCatching {
                // error
            }
        }
    }
}

     */

    override suspend fun insertUsers(users: List<User>) = userDao.insertUser(users)
    override fun getAllBarbers(): Flow<List<Barber>> = barberDao.getAllBarbers()

    override fun getBarberById(barberId: Int): Flow<Barber> = barberDao.getBarberById(barberId)

    override suspend fun insertBarber(barbers: List<Barber>) = barberDao.insertBarber(barbers)
    override fun getAllServices(): Flow<List<Service>> = serviceDao.getAllServices()

    override fun getServiceById(serviceId: Int): Flow<Service> = serviceDao.getServiceById(serviceId)

    override suspend fun insertService(service: List<Service>) = serviceDao.insertService(service)

    override fun getServicePrices(serviceIds: List<Int>): Flow<List<Double>> = serviceDao.getServicePrices(serviceIds)
    override suspend fun insertAppointment(appointment: Appointment): Long = appointmentDao.insertAppointment(appointment)

    override suspend fun insertAppointmentServiceCrossRef(crossRef: AppointmentServiceCrossRef) = appointmentDao.insertAppointmentServiceCrossRef(crossRef)

    override fun getAppointmentWithServices(appointmentId: Int): Flow<AppointmentWithServices> = appointmentDao.getAppointmentWithServices(appointmentId)

    override fun getAppointmentsForUser(userId: Int): Flow<List<Appointment>> = appointmentDao.getAppointmentsForUser(userId)

    override suspend fun updateAppointmentStatus(appointmentId: Int, status: Status) = appointmentDao.updateAppointmentStatus(appointmentId, status)

}