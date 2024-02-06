package com.example.svetlogorskchpp.electricalAssembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DialogAssemblyViewModelFactory(private val idElectricalAssembly: String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DialogFragmentAssemblyViewModel::class.java)) {
            return DialogFragmentAssemblyViewModel(idElectricalAssembly) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}