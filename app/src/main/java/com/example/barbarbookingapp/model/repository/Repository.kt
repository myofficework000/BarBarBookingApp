package com.example.barbarbookingapp.model.repository

import com.example.barbarbookingapp.model.ResultState
import com.example.barbarbookingapp.model.dao.UserDao
import com.example.barbarbookingapp.model.dto.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository
    @Inject constructor(
        private val userDao: UserDao
    ): IRepository {
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

    override suspend fun insertUsers(users: List<User>) {
        userDao.insertUser(users)
    }
}