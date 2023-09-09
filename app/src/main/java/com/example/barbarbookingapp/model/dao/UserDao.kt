package com.example.barbarbookingapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.barbarbookingapp.model.dto.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAllUser():List<User>

    @Query("SELECT * FROM users WHERE  userId = :userId")
    suspend fun getUser(userId:Int):User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<User>)
}