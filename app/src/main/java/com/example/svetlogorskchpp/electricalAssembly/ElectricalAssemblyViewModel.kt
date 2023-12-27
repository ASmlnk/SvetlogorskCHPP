package com.example.svetlogorskchpp.electricalAssembly

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.SharedPreferencesManager
import com.example.svetlogorskchpp.model.ElectricalAssemblyFirebase
import com.example.svetlogorskchpp.model.UpdateDateFB
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.GregorianCalendar
import java.util.TimeZone

class ElectricalAssemblyViewModel : ViewModel() {

    private val data = FirestoreRepository.get()
    private val listAllAssembly = mutableListOf<ElectricalAssemblyFirebase>()
    private val _liveDataAssembly = MutableLiveData<List<ElectricalAssemblyFirebase>>()
    val liveDataAssembly: LiveData<List<ElectricalAssemblyFirebase>>
        get() = _liveDataAssembly

    private val _listFilterAssembly = MutableLiveData<List<ElectricalAssemblyFirebase>>()
    val listFilterAssembly: LiveData<List<ElectricalAssemblyFirebase>>
        get() = _listFilterAssembly

    private val _navigateToDialogAssembly = MutableLiveData<String?>()
    val navigateToDialogAssembly: LiveData<String?>
        get() = _navigateToDialogAssembly

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isUpdate = data.getUpdateDateElectricAssembly()?.let {
                equalsDate(it)
            } ?: false

            if (isUpdate) {
                SharedPreferencesManager.saveString("date_electric_assembly", date())
                val listData = data.getAllElectricalAssemblyServer()
                withContext(Dispatchers.Main) {
                    listAllAssembly.addAll(listData)
                    _liveDataAssembly.value = listData
                }
            } else {
                val listData = data.getAllElectricalAssemblyCache()
                withContext(Dispatchers.Main) {
                    listAllAssembly.addAll(listData)
                    _liveDataAssembly.value = listData
                }
            }
        }
    }

    fun onClickedDialogAssembly(itemId: String) {
        _navigateToDialogAssembly.value = itemId
    }

    fun onDialogNavigation() {
        _navigateToDialogAssembly.value = null
    }

    fun getFilterList(filter: String) {
        val listFilter = when (filter) {
            "ХЦ" -> listAllAssembly.filter { it.nameDepartment == "ХЦ" }
            "КТЦ т/о" -> listAllAssembly.filter { it.nameDepartment == "КТЦ т/о" }
            "КТЦ к/о" -> listAllAssembly.filter { it.nameDepartment == "КТЦ к/о" }
            "БНС" -> listAllAssembly.filter { it.nameDepartment == "БНС" }
            "Градирня" -> listAllAssembly.filter { it.nameDepartment == "Градирня" }
            "Щит. блок" -> listAllAssembly.filter { it.nameDepartment == "Щит. блок" }
            "КРУ" -> listAllAssembly.filter { it.nameDepartment == "КРУ" }
            "РУ" -> listAllAssembly.filter { it.nameDepartment == "РУ" }
            "Все" -> listAllAssembly

            else -> emptyList()
        }
        _listFilterAssembly.value = listFilter.sortedBy { it.nameAssembly }
    }

    fun equalsDate(dateFB: UpdateDateFB): Boolean {

        GregorianCalendar()
        val dateUpdateDateFB = GregorianCalendar(
            dateFB.year.toInt(),
            dateFB.month.toInt(),
            dateFB.dayMonth.toInt()
        ).timeInMillis

        val dateCash = SharedPreferencesManager.getString("date_electric_assembly", "0").toLong()
        return dateUpdateDateFB > dateCash
    }

    fun date(): String {
        val calendar = GregorianCalendar
            .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

        return calendar.timeInMillis.toString()
    }

    fun textNameElectricalAssembly(nameDepartment: String): String {
        val listName =
            listAllAssembly.filter { it.nameDepartment == nameDepartment }.map { it.nameAssembly }
        var textName = ""
        for (item in listName) {
            textName = textName + item + "\n"
        }
        return textName
    }

    fun dialogElectricalAssembly(id: String) {
        val electricalAssembly = listAllAssembly.filter { it.id == id }

        electricalAssembly?.let {
            // _dialogElectricalAssembly.value = it
        }
    }
}