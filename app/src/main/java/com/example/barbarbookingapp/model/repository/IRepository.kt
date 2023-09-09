package com.example.barbarbookingapp.model.repository

import com.example.barbarbookingapp.model.ResultState
import com.example.barbarbookingapp.model.dto.User
import kotlinx.coroutines.flow.Flow

interface IRepository {

    //function for User Dao
    suspend fun getAllUser(): Flow<ResultState<List<User>>>
    suspend fun insertUsers(users: List<User>)

}
