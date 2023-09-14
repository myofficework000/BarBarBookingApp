package com.example.barbarbookingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.barbarbookingapp.model.dto.Appointment
import com.example.barbarbookingapp.model.dto.AppointmentWithServices
import com.example.barbarbookingapp.model.dto.Barber
import com.example.barbarbookingapp.model.dto.Service
import com.example.barbarbookingapp.model.dto.Status
import com.example.barbarbookingapp.model.dto.User
import com.example.barbarbookingapp.model.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BarberViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    val users: LiveData<List<User>> = repository.getAllUsers().asLiveData()
    val barbers: LiveData<List<Barber>> = repository.getAllBarbers().asLiveData()
    val services: LiveData<List<Service>> = repository.getAllServices().asLiveData()

    private val _selectedUserId = MutableLiveData<Int>()
    val selectedUser: LiveData<User> = _selectedUserId.switchMap { id ->
        repository.getUserById(id).asLiveData()
    }
    val appointmentsForUser: LiveData<List<Appointment>> = _selectedUserId.switchMap { id ->
        repository.getAppointmentsForUser(id).asLiveData()
    }

    private val _selectedBarberId = MutableLiveData<Int>()
    val selectedBarber: LiveData<Barber> = _selectedBarberId.switchMap { id ->
        repository.getBarberById(id).asLiveData()
    }

    private val _selectedServiceId = MutableLiveData<Int>()
    val selectedService: LiveData<Service> = _selectedServiceId.switchMap { id ->
        repository.getServiceById(id).asLiveData()
    }

    //appointment start time
    private var _selectedStartTime = MutableLiveData<Pair<Int, Int>>()
    var selectedStartTime = _selectedStartTime

    private var _selectedDate = MutableLiveData<Date>()
    var selectedDate = _selectedDate

    //history appointments, testing user id 2
    private var _allAppointments = MutableLiveData<List<AppointmentWithServices>>()
    var allAppointments = _allAppointments

    //services list
    private var _allServices = repository.getAllServices()
    var allServices = _allServices.asLiveData()

    //appointment selected services
    private val _selectedServices = MutableLiveData<List<Service>>()
    var selectedServices: LiveData<List<Service>> = _selectedServices
    fun setSelectedServices(list: List<Service>) {
        _selectedServices.postValue(list)
    }

    fun setStartTime(startTime: Pair<Int, Int>) {
        _selectedStartTime.postValue(startTime)
    }

    fun setSelectedDate(date: Date) {
        _selectedDate.postValue(date)
    }

    suspend fun fetchHistoryAppointmentForUser(userId: Int){
        repository.getAppointmentsForUser(userId)
            .map { appts ->
                Log.i("flow", "now processing appts")
                appts.map { it.appointmentId }.toList()
            }
            .flatMapConcat { idList ->
                Log.i("flow", "now processing idlist")
                flow {
                    val appointmentList = idList.map { id ->
                        Log.i("flow", "now processing $id")
                        repository.getAppointmentWithServices(id)
                            .first() // first() to get first value for each id
                    }
                    emit(appointmentList)
                }
            }.collect{
                _allAppointments.postValue(it)
            }
    }

    private val _selectedAppointmentId = MutableLiveData<Int>()
    val selectedAppointmentWithServices: LiveData<AppointmentWithServices> =
        _selectedAppointmentId.switchMap { id ->
            repository.getAppointmentWithServices(id).asLiveData()
        }
    val selectedAppointmentDuration: LiveData<Int?> = _selectedAppointmentId.switchMap { id ->
        repository.getAppointmentDuration(id).asLiveData()
    }

    fun updateAppointmentStatus(appointmentId: Int, status: Status) = viewModelScope.launch {
        repository.updateAppointmentStatus(appointmentId, status)
    }

    fun selectedUserId(userId: Int) {
        _selectedUserId.value = userId
    }

    fun selectedBarberId(barberId: Int) {
        _selectedBarberId.value = barberId
    }

    fun selectedServiceId(serviceId: Int) {
        _selectedServiceId.value = serviceId
    }

    fun selectedAppointmentId(appointmentId: Int) {
        _selectedAppointmentId.value = appointmentId
    }

    fun insertUser(users: List<User>) = viewModelScope.launch {
        repository.insertUsers(users)
    }

    fun insertBarber(barbers: List<Barber>) = viewModelScope.launch {
        repository.insertBarber(barbers)
    }

    fun insertService(services: List<Service>) = viewModelScope.launch {
        repository.insertService(services)
    }

}