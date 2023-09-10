package com.example.barbarbookingapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.barbarbookingapp.model.dao.AppointmentDao
import com.example.barbarbookingapp.model.dao.BarberDao
import com.example.barbarbookingapp.model.dao.ServiceDao
import com.example.barbarbookingapp.model.dao.UserDao
import com.example.barbarbookingapp.model.dto.Appointment
import com.example.barbarbookingapp.model.dto.AppointmentServiceCrossRef
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.model.dto.User

@Database(entities = [User::class, Service::class, Barber::class, Appointment::class, AppointmentServiceCrossRef::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getServiceDao(): ServiceDao
    abstract fun getBarberDao(): BarberDao
    abstract fun getAppointmentDao(): AppointmentDao

    companion object{
        private const val DB_NAME="BarberApp"
        private var DATABASE_INSTANCE : AppDatabase?=null

        fun getAppDatabase(context: Context): AppDatabase?{
            if(DATABASE_INSTANCE ==null){
                DATABASE_INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(dataCallback(context.applicationContext))
                    .build()
            }
            return DATABASE_INSTANCE
        }
    }
}