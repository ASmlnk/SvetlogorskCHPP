package com.example.svetlogorskchpp.transformerOfOwnNeeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.powerLines.PowerLines
import com.example.svetlogorskchpp.model.transformerNeeds.Tsn
import com.example.svetlogorskchpp.model.transformerNeeds.TsnData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransformerOfOwnNeedsViewModel: ViewModel() {

    private val data = TsnData()
    private val listTsn = mutableListOf<Tsn>()
    private val _liveData = MutableLiveData<List<Tsn>>()
    val liveData: LiveData<List<Tsn>>
        get() = _liveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = data.getList()
            withContext(Dispatchers.Main) {
                listTsn.addAll(list)
                _liveData.value = list
            }
        }
    }

    fun getFilterList(filter: String) {
        val listFilter = when (filter) {
            "РУСН-0,4 кВ" -> listTsn.filter { it.lowVoltage == "РУСН-0,4 кВ" }
            "КРУ-3,15 кВ" -> listTsn.filter { it.lowVoltage == "КРУ-3,15 кВ" }
            "КРУ-6,3 кВ" -> listTsn.filter { it.lowVoltage == "КРУ-6,3 кВ" }
            else -> listTsn
        }
        _liveData.value = listFilter
    }
}