package com.example.barbarbookingapp.model.repository

import com.example.barbarbookingapp.model.ResultState
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.User
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun getAllUsers(): Flow<List<User>>
    fun getUserById(userId: Int): Flow<User>
    suspend fun insertUsers(users: List<User>)

    fun getAllBarbers(): Flow<List<Barber>>
    fun getBarberById(barberId: Int): Flow<Barber>
    suspend fun insertBarber(barbers: List<Barber>)

}
