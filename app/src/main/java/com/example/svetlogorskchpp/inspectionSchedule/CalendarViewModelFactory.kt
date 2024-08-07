package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import java.util.Date

class CalendarViewModelFactory(private val date: Date, private val data: FirestoreRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)){
            return CalendarViewModel(date, data) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}