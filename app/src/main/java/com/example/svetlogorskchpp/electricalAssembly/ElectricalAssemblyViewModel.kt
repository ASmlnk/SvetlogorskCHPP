package com.example.svetlogorskchpp.electricalAssembly

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.SharedPreferencesManager
import com.example.svetlogorskchpp.model.ElectricalAssemblyFirebase
import com.example.svetlogorskchpp.model.ElectricalAssemblySearch
import com.example.svetlogorskchpp.model.UpdateDateFB
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotorSearch
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.example.svetlogorskchpp.zeroVision.ZeroVisionType
import com.google.gson.Gson
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.grpc.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.GregorianCalendar
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class ElectricalAssemblyViewModel @Inject constructor(
    application: Application,
    private val data: FirestoreRepository,
): AndroidViewModel(application) {

   // private val data = FirestoreRepository.get()
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

    private val _listSearchLiveData = MutableLiveData<List<ElectricalAssemblySearch>>()
    val listSearchLiveData: LiveData<List<ElectricalAssemblySearch>>
        get() = _listSearchLiveData

    private val _searchStateFlow = MutableStateFlow(false)
    val searchStateFlow: StateFlow<Boolean>
        get() = _searchStateFlow

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
        val listFilter = if (filter == "Все") listAllAssembly else listAllAssembly.filter { it.nameDepartment == filter }


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

    fun search(textSearch: String) {
        if (textSearch == "") {
            _listSearchLiveData.value = emptyList()
        } else {
            if (textSearch == "files") {
                val list = mutableMapOf<String, String>()
                for (electricalAssembly in listAllAssembly) {
                    val listItem = electricalAssembly.listItemAssembly.keys.toList()
                    for (i in listItem) {
                        list.put(i, electricalAssembly.nameAssembly)
                    }
                }
                val listR = list.toSortedMap()
                val dd = Gson().toJson(listR)
                val context = getApplication<Application>().applicationContext
                val file = File(context.filesDir, "filename")

                FileOutputStream(file).use { outputStream ->
                    outputStream.write(dd.toByteArray())
                }
            }


            val text = textSearch.lowercase()
            val list = mutableListOf<ElectricalAssemblySearch>()
            for (electricalAssembly in listAllAssembly) {
                val listItem = electricalAssembly.listItemAssembly.keys.toList()   //lowercase().indexOf(text)
                for (i in listItem) {
                    val index = i.lowercase().indexOf(text)
                    if (index >= 0) list.add(ElectricalAssemblySearch(name=i, index = index, electricalAssembly = electricalAssembly))
                }
            }
            _listSearchLiveData.value = list.sortedBy { it.name }.sortedBy { it.index }
        }
    }

    fun openSearch() {
            _searchStateFlow.value = true
    }

    fun closeSearch() {
            _searchStateFlow.value = false
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