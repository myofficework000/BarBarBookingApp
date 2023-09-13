package com.example.barbarbookingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbarbookingapp.model.dto.User
import com.example.barbarbookingapp.model.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    private var _allUsers = MutableLiveData<List<User>>()
    var allUsers:LiveData<List<User>> = _allUsers

    fun insertUser(users: List<User>) = viewModelScope.launch {
        repository.insertUsers(users)
    }

    suspend fun fetchAllUsers() = viewModelScope.launch {
        repository.getAllUsers().collect { users ->
            _allUsers.postValue(users)
        }
    }

}