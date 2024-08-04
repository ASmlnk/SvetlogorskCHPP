package com.example.svetlogorskchpp.electricalAssembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository

class DialogAssemblyViewModelFactory(private val idElectricalAssembly: String, private val data: FirestoreRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogFragmentAssemblyViewModel::class.java)) {
            return DialogFragmentAssemblyViewModel(idElectricalAssembly, data) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}