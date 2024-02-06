package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CheckListInspectionViewModelFactory(private val nameCategory: String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChecklistInspectionViewModel::class.java)) {
            return ChecklistInspectionViewModel(nameCategory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}