package com.example.svetlogorskchpp.openSwitchgear

import androidx.lifecycle.LiveData
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
    private val listPowerLines = mutableListOf<PowerLines>()

    private val _liveData = MutableLiveData<List<PowerLines>>()
    val liveData: LiveData<List<PowerLines>>
        get() = _liveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = data.getAllPowerLines()
           withContext(Dispatchers.Main) {
               listPowerLines.addAll(list)
               _liveData.value = list
           }
        }
    }

    fun getFilterList(filter: String) {
        val listFilter = when (filter) {
            "ОРУ-110" -> listPowerLines.filter { it.nameORY == "ОРУ-110" }
            "ОРУ-220" -> listPowerLines.filter { it.nameORY == "ОРУ-220" }
            else -> listPowerLines
        }
        _liveData.value = listFilter
    }
}