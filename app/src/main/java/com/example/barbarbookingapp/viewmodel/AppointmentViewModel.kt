package com.example.barbarbookingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.barbarbookingapp.model.dto.Appointment
import com.example.barbarbookingapp.model.dto.AppointmentWithServices
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.model.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(repository: Repository) : ViewModel() {
    //appointment start time
    private var _selectedStartTime = MutableLiveData<Pair<Int, Int>>()
    var selectedStartTime = _selectedStartTime

    //services list
    private var _allServices = repository.getAllServices()
    var allServices = _allServices.asLiveData()

    //appointment selected services
    private val _selectedServices = MutableLiveData<List<Service>>()
    var selectedServices:LiveData<List<Service>> = _selectedServices
    fun setSelectedServices(list:List<Service>){
        _selectedServices.postValue(list)
    }

    fun setStartTime(startTime: Pair<Int, Int>) {
        _selectedStartTime.postValue(startTime)
    }
}