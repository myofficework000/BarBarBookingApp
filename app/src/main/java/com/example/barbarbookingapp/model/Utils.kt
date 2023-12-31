package com.example.barbarbookingapp.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {
    @SuppressLint("SimpleDateFormat")
    fun String.formatDateFromString(pattern: String): Date? {
        val dateFormat = SimpleDateFormat(pattern)
        var date: Date? = null
        try {
            date = dateFormat.parse(this)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun Date.formatDateIntoString(pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(this)
    }

    fun Pair<Int,Int>.formatTimeIntoString():String{
        var minutes = "${this.second}"
        if (this.second / 10 == 0) minutes = "0${this.second}"
        return "${this.first}:${minutes}"
    }

    fun Date.isDate(date:Date):Boolean{
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = this

        val compareCalendar = Calendar.getInstance()
        compareCalendar.time = date

        return currentCalendar.get(Calendar.YEAR) == compareCalendar.get(Calendar.YEAR) &&
                currentCalendar.get(Calendar.MONTH) == compareCalendar.get(Calendar.MONTH) &&
                currentCalendar.get(Calendar.DAY_OF_MONTH) == compareCalendar.get(Calendar.DAY_OF_MONTH)
    }

    fun String.formatIntoTime():Pair<Int,Int>{
        val time = this.split(":")
        return Pair(time[0].toInt(),time[1].toInt())
    }

    fun makeToast(context: Context, msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
}