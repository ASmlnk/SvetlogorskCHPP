package com.example.svetlogorskchpp.openSwitchgear

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.powerLines.OverheadPowerLines
import com.example.svetlogorskchpp.model.powerLines.PowerLines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OpenSwitchgearViewModel: ViewModel() {

    private val data = OverheadPowerLines()
    val liveData = MutableLiveData<List<PowerLines>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = data.getAllPowerLines()
           withContext(Dispatchers.Main) {
               liveData.value = list
           }
        }
    }




}