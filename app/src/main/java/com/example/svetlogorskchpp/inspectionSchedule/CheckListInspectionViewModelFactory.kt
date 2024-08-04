package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository

class CheckListInspectionViewModelFactory(private val nameCategory: String, private val data: FirestoreRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChecklistInspectionViewModel::class.java)) {
            return ChecklistInspectionViewModel(nameCategory, data) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}