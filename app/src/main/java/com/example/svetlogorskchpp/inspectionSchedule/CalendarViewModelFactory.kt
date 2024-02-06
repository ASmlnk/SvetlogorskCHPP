package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar
import java.util.Date

class CalendarViewModelFactory(private val date: Date): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            return CalendarViewModel(date) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}