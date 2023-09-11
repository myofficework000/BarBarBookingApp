package com.example.barbarbookingapp.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.barbarbookingapp.model.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectTimeViewModel@Inject constructor() :ViewModel() {
    private var _selectedStartTime = MutableLiveData<Pair<Int,Int>>()
    var selectedStartTime = _selectedStartTime

    private var _backGroundColor = MutableLiveData<Color>()
    var backGroundColor = _backGroundColor

    fun setStartTime(startTime:Pair<Int,Int>){
        _selectedStartTime.postValue(startTime)
    }

    fun setBackGroundColor(color: Color){
        _backGroundColor.postValue(color)
    }
}