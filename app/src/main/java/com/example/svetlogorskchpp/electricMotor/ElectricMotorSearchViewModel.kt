package com.example.svetlogorskchpp.electricMotor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.SharedPreferencesManager
import com.example.svetlogorskchpp.model.UpdateDateFB
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotorSearch
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

class ElectricMotorSearchViewModel: ViewModel() {

    val data = FirestoreRepository.get()
    private val listAll = mutableListOf<ElectricMotor>()
    private val _listSearchLiveData = MutableLiveData<List<ElectricMotor>>()
    val listSearchLiveData: LiveData<List<ElectricMotor>> get() = _listSearchLiveData

    private val _dataElectricMotor = MutableLiveData<List<ElectricMotor>>()
    val dataElectricMotor: LiveData<List<ElectricMotor>> get() = _dataElectricMotor


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isUpdate = data.getUpdateDateElectricMotor()?.let {
                equalsDate(it)
            } ?: false

            if (isUpdate) {
                SharedPreferencesManager.saveString("date_electric_motor1", date())
                val dataList = data.getAllElectricMotorServer()
                withContext(Dispatchers.Main) {
                    listAll.addAll(dataList)
                    _dataElectricMotor.value = dataList
                }
            } else {
                val dataList =  data.getAllElectricMotorCache()
                withContext(Dispatchers.Main) {
                    listAll.addAll(dataList)
                    _dataElectricMotor.value = dataList
                }
            }
        }
    }

    private fun date(): String {
        val calendar = GregorianCalendar
            .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

        val data = calendar.timeInMillis

        return data.toString()
    }

    private fun equalsDate(dateFB: UpdateDateFB): Boolean {

        val dateUpdateDateFB = GregorianCalendar(
            dateFB.year.toInt(),
            dateFB.month.toInt(),
            dateFB.dayMonth.toInt()
        ).timeInMillis

        val dateCash = SharedPreferencesManager.getString("date_electric_motor1", "0").toLong()
        return dateUpdateDateFB > dateCash
    }

    fun search(textSearch: String) {
        if (textSearch == "") {
            _listSearchLiveData.value = emptyList()
        } else {
            val text = textSearch.lowercase()
            val list = mutableListOf<ElectricMotorSearch>()
            for (i in listAll) {
                val index = i.name.lowercase().indexOf(text)
                if (index >= 0) list.add(ElectricMotorSearch(electricMotor = i, index = index))
            }

            _listSearchLiveData.value = list.sortedBy { it.index }.map { it.electricMotor }
        }
    }
}