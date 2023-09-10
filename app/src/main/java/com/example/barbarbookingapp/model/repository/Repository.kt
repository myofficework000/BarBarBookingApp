package com.example.barbarbookingapp.model.repository

import com.example.barbarbookingapp.model.ResultState
import com.example.barbarbookingapp.model.dao.BarberDao
import com.example.barbarbookingapp.model.dao.UserDao
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository
    @Inject constructor(
        private val userDao: UserDao,
        private val barberDao: BarberDao
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

}