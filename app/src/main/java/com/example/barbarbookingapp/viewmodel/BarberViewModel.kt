package com.example.barbarbookingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.User
import com.example.barbarbookingapp.model.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarberViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    val users: LiveData<List<User>> = repository.getAllUsers().asLiveData()
    val barbers: LiveData<List<Barber>> = repository.getAllBarbers().asLiveData()

    private val _selectedUserId = MutableLiveData<Int>()
    val selectedUser: LiveData<User> = _selectedUserId.switchMap { id ->
        repository.getUserById(id).asLiveData()
    }

    private val _selectedBarberId = MutableLiveData<Int>()
    val selectedBarber: LiveData<Barber> = _selectedBarberId.switchMap { id ->
        repository.getBarberById(id).asLiveData()
    }

    fun selectedUserId(userId: Int) {
        _selectedUserId.value = userId
    }

    fun selectedBarberId(barberId: Int) {
        _selectedBarberId.value = barberId
    }

    fun insertUser(users: List<User>) = viewModelScope.launch {
        repository.insertUsers(users)
    }

    fun insertBarber(barbers: List<Barber>) = viewModelScope.launch {
        repository.insertBarber(barbers)
    }



}