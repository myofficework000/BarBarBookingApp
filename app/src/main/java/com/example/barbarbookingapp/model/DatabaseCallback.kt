package com.example.barbarbookingapp.model

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.barbarbookingapp.model.dto.Appointment
import com.example.barbarbookingapp.model.dto.AppointmentServiceCrossRef
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.PaymentMethod
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.model.dto.Status
import com.example.barbarbookingapp.model.dto.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun dataCallback(context: Context) = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            val userDao = AppDatabase.getAppDatabase(context)!!.getUserDao()
            val serviceDao = AppDatabase.getAppDatabase(context)!!.getServiceDao()
            val barberDao = AppDatabase.getAppDatabase(context)!!.getBarberDao()
            val appointmentDao = AppDatabase.getAppDatabase(context)!!.getAppointmentDao()
            userDao.insertUser(
                listOf(
                    User(firstName = "John", lastName = "Doe", email = "john@mail.com", password = "12345"),
                    User(firstName = "Jane", lastName = "Doe", email = "jane@mail.com", password = "12345")
                )
            )
            serviceDao.insertService(
                listOf(
                    Service(name = "Men's Cut", duration = 20, price = 30.0),
                    Service(name = "Women's Cut", duration = 30, price = 40.0),
                    Service(name = "Kid's Cut", duration = 20, price = 25.0),
                    Service(name = "Beard Trim", duration = 15, price = 20.0),
                    Service(name = "Dry Shave", duration = 15, price = 15.0),
                    Service(name = "Hot Towel Shave", duration = 25, price = 30.0),
                    Service(name = "Facial", duration = 60, price = 50.0),
                )
            )
            barberDao.insertBarber(
                listOf(
                    Barber(firstName = "James", lastName = "McBarber", experience = "James has over 15 years of experience in classic and contemporary styles, specializing in fades and beard grooming.", rating = 4),
                    Barber(firstName = "Lisa", lastName = "ShearGenius", experience = "With a career spanning 10 years, Lisa has a knack for creating the perfect hairstyle for every face shape, and is known for her excellent scissor work.", rating = 4),
                    Barber(firstName = "Carlos", lastName = "Vasquez", experience = "Carlos, with 20 years in the industry, is a master of blade work. His straight razor shaves are legendary in the community.", rating = 5),
                    Barber(firstName = "Amina", lastName = "CurlWhisperer", experience = "Amina has 12 years of experience and is particularly known for her expertise in handling curly hair with the utmost care.", rating = 3),
                    Barber(firstName = "Derek", lastName = "TrendSetter", experience = "Derek, a barber with 8 years of experience, is always ahead of the curve when it comes to new trends and styles.", rating = 5)
                )
            )
            val servicePrices1 = serviceDao.getServicePrices(listOf(1,2)).first()
            val servicePrices2 = serviceDao.getServicePrices(listOf(3,4)).first()
            val totalServiceCharge1 = servicePrices1.sum()
            val totalServiceCharge2 = servicePrices2.sum()
            val appointment1 = Appointment(userId = 1, barberId = 1, appointmentDate = "2023-08-30", appointmentTime = "10:00", serviceCharge = totalServiceCharge1, status = Status.COMPLETED, paymentMethod = PaymentMethod.CASH)
            val appointment2 = Appointment(userId = 2, barberId = 3, appointmentDate = "2023-09-30", appointmentTime = "14:00", serviceCharge = totalServiceCharge2, status = Status.SCHEDULED, paymentMethod = PaymentMethod.CASH)
            val appointmentId1 = appointmentDao.insertAppointment(appointment1)
            val appointmentId2 = appointmentDao.insertAppointment(appointment2)
            appointmentDao.insertAppointmentServiceCrossRef(AppointmentServiceCrossRef(appointmentId = appointmentId1.toInt(), serviceId = 1))
            appointmentDao.insertAppointmentServiceCrossRef(AppointmentServiceCrossRef(appointmentId = appointmentId1.toInt(), serviceId = 2))
            appointmentDao.insertAppointmentServiceCrossRef(AppointmentServiceCrossRef(appointmentId = appointmentId2.toInt(), serviceId = 3))
            appointmentDao.insertAppointmentServiceCrossRef(AppointmentServiceCrossRef(appointmentId = appointmentId2.toInt(), serviceId = 4))
        }
    }
}
