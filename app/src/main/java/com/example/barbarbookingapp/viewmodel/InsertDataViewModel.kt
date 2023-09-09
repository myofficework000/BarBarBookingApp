package com.example.barbarbookingapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbarbookingapp.model.dto.User
import com.example.barbarbookingapp.model.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertDataViewModel @Inject constructor(
    private val iRepository: IRepository
) : ViewModel() {

    fun initDb(){
        insertUser()
    }

    fun insertUser() {
        viewModelScope.launch {
            try {
                iRepository.insertUsers(listOfUsers())
            }catch (e: Exception){
                Log.i("InitializeDataViewModel", e.printStackTrace().toString())
            }
        }
    }

    fun listOfUsers() = listOf(
        User(1, "Amar", "Sapcanin", "amar@gmail.com", "12345678")
    )
}