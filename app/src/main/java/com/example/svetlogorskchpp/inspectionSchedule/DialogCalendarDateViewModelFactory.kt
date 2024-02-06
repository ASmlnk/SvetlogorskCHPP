package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.Date

class DialogCalendarDateViewModelFactory(private val date: Date, private val workingShift: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogCalendarDateViewModel::class.java)) {
            return DialogCalendarDateViewModel(date, workingShift) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}