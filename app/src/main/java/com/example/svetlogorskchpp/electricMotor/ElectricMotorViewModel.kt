package com.example.svetlogorskchpp.electricMotor

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.SharedPreferencesManager
import com.example.svetlogorskchpp.model.UpdateDateFB
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.TimeZone

class ElectricMotorViewModel : ViewModel() {

    val data = FirestoreRepository.get()

    private val listAll = mutableListOf<ElectricMotor>()
    val listFilterLiveData = MutableLiveData<List<ElectricMotor>>()
    val dataElectricMotor = MutableLiveData<List<ElectricMotor>>()

    init {
        viewModelScope.launch {
            val date = async {  data.getUpdateDateFB()}
            val b = equalsDate(date.await()!!)

            if (b) {
                SharedPreferencesManager.saveString("date_electric_motor1", date())
                val dataList = data.getAllElectricMotorServer()
                listAll.addAll(dataList)
                dataElectricMotor.value = dataList

            } else {
               val dataList =  data.getAllElectricMotorCache()
                listAll.addAll(dataList)
                dataElectricMotor.value = dataList
            }
        }
    }

    fun getFilterList(filter: String) {
        val listFilter = when (filter) {
            "380" -> listAll.filter { it.voltage == "380" }
            "3000" -> listAll.filter { it.voltage == "3000" }
            "6000" -> listAll.filter { it.voltage == "6000" }
            "all" -> listAll
            "ТГ-1" -> listAll.filter { it.category == "ТГ-1" }
            "ТГ-3" -> listAll.filter { it.category == "ТГ-3" }
            "ТГ-4" -> listAll.filter { it.category == "ТГ-4" }
            "ТГ-5" -> listAll.filter { it.category == "ТГ-5" }
            "ТГ-6" -> listAll.filter { it.category == "ТГ-6" }
            "ТГ" -> listAll.filter { it.generalCategory == "ТГ" }
            "К/А-1" -> listAll.filter { it.category == "К/А-1" }
            "К/А-6" -> listAll.filter { it.category == "К/А-6" }
            "К/А-7" -> listAll.filter { it.category == "К/А-7" }
            "К/А-8" -> listAll.filter { it.category == "К/А-8" }
            "К/А-9" -> listAll.filter { it.category == "К/А-9" }
            "К/А" -> listAll.filter { it.generalCategory == "К/А" }
            "КТЦ т/о" -> listAll.filter { it.generalCategory == "КТЦ т/о" }
            "ПЭН" -> listAll.filter { it.category == "ПЭН" }
            "СН" -> listAll.filter { it.category == "СН" }
            "БНТ" -> listAll.filter { it.category == "БНТ" }
            "ПН" -> listAll.filter { it.category == "ПН" }
            "НДВ" -> listAll.filter { it.category == "НДВ" }
            "ЦН" -> listAll.filter { it.category == "ЦН" }
            "КТЦ к/о" -> listAll.filter { it.generalCategory == "КТЦ к/о" }
            "Багерная" -> listAll.filter { it.category == "Багерная" }
            "ХОВ" -> listAll.filter { it.generalCategory == "ХОВ" }
            "ТУ" -> listAll.filter { it.category == "ТУ" }
            "РЭП" -> listAll.filter { it.rep }
            "К3-1" -> listAll.filter { it.indexSection == "К3-1" }
            "К3-3" -> listAll.filter { it.indexSection == "К3-3" }
            "К3-4" -> listAll.filter { it.indexSection == "К3-4" }
            "К3-5" -> listAll.filter { it.indexSection == "К3-5" }
            "К3-6" -> listAll.filter { it.indexSection == "К3-6" }
            "К3-7" -> listAll.filter { it.indexSection == "К3-7" }
            "К3-8" -> listAll.filter { it.indexSection == "К3-8" }
            "К3-9" -> listAll.filter { it.indexSection == "К3-9" }
            "К6-1" -> listAll.filter { it.indexSection == "К6-1" }
            "К6-2" -> listAll.filter { it.indexSection == "К6-2" }
            "К6-3" -> listAll.filter { it.indexSection == "К6-3" }
            "Р-1" -> listAll.filter { it.indexSection == "Р-1" }
            "Р-2" -> listAll.filter { it.indexSection == "Р-2" }
            "Р-3" -> listAll.filter { it.indexSection == "Р-3" }
            "Р-4" -> listAll.filter { it.indexSection == "Р-4" }
            "Р-5" -> listAll.filter { it.indexSection == "Р-5" }
            "Р-6" -> listAll.filter { it.indexSection == "Р-6" }
            "Р-7" -> listAll.filter { it.indexSection == "Р-7" }

            else -> emptyList()
        }
        listFilterLiveData.value = listFilter
    }

    fun date(): String {
        val calendar = GregorianCalendar
            .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

        /*val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)*/
        val data = calendar.timeInMillis

        return data.toString()
    }

    fun getDataPref(): String {
        val date = SharedPreferencesManager.getString("date_electric_motor1", "0")
        return dateFormat(date.toLong())
    }

    fun equalsDate(dateFB: UpdateDateFB): Boolean {
        GregorianCalendar()

        val dateUpdateDateFB = GregorianCalendar(
            dateFB.year.toInt(),
            dateFB.month.toInt(),
            dateFB.dayMonth.toInt()
        ).timeInMillis

        val dateCash = SharedPreferencesManager.getString("date_electric_motor1", "0").toLong()
        return dateUpdateDateFB > dateCash
    }

    fun dateFormat(date: Long): String = SimpleDateFormat("dd.MM.yyyy HH:mm").format(date)
}