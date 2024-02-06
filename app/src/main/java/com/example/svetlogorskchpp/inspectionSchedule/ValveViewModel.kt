package com.example.svetlogorskchpp.inspectionSchedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc
import kotlinx.coroutines.launch

class ValveViewModel(): ViewModel() {
    val data = FirestoreRepository.get()

    init {
        viewModelScope.launch {
            data.getChecklistInspection(InSc.VALVE.get)
        }
    }

    override fun onCleared() {
        super.onCleared()
        data.clearListValue()
    }
}