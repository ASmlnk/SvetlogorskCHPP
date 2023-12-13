package com.example.svetlogorskchpp.transformerOfOwnNeeds

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
    val liveData = MutableLiveData<List<Tsn>>()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            val list = data.getList()
            withContext(Dispatchers.Main) {
                liveData.value = list
            }
        }
    }
}