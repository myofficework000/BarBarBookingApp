package com.example.barbarbookingapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.barbarbookingapp.model.dao.UserDao
import com.example.barbarbookingapp.model.dto.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object{
        private const val DB_NAME="BarberApp"
        private var DATABASE_INSTANCE : AppDatabase?=null

        fun getAppDatabase(context: Context): AppDatabase?{
            if(DATABASE_INSTANCE ==null){
                DATABASE_INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DATABASE_INSTANCE
        }
    }
}