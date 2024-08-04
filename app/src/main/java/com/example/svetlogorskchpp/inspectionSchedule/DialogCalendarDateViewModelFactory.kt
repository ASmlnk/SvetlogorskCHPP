package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import java.util.Date

class DialogCalendarDateViewModelFactory(private val date: Date, private val workingShift: String, private val data: FirestoreRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogCalendarDateViewModel::class.java)) {
            return DialogCalendarDateViewModel(date, workingShift, data) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}