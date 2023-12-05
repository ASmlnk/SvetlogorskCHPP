package com.example.svetlogorskchpp.electricMotor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ElectricMotorViewModel : ViewModel() {

    val data = FirestoreRepository()
    private val listAll = mutableListOf<ElectricMotor>()
    val boilerUnitLiveData = MutableLiveData<List<ElectricMotor>>()

    init {
        viewModelScope.launch {
            getAll()
        }
    }

    suspend fun getAll() {

        val list = mutableListOf<ElectricMotor>()

        val dataKa = data.getAll("Котлоагрегаты").get()
        val listKa = dataKa.await()
        for (it in listKa) {
            list.add(it.toObject<ElectricMotor>().apply { id = it.id })
        }

        val dataT = data.getAll("Турбогенераторы").get()
        val listT = dataT.await()            //.toObjects<ElectricMotor>()

        for (it in listT) {
            list.add(it.toObject<ElectricMotor>().apply { id = it.id })
        }

        val dataOther = data.getAll("Остальное").get()
        val listOther = dataOther.await()                 //.toObjects<ElectricMotor>()
        for (it in listOther) {
            list.add(it.toObject<ElectricMotor>().apply { id = it.id })
        }
        //mutableListOf<ElectricMotor>()
        //val list = listKa + listT+listOther


        /* list.apply {
             addAll(listKa)
             addAll(listT)
             addAll(listOther)
         }*/
        val list2 = list.sortedBy { it.name }
        listAll.addAll(list2)
    }

    fun getFilterList(filter: String) {
      val listFilter =   when (filter) {
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

        boilerUnitLiveData.value = listFilter
    }

    fun getAllChip() {
        val list = listAll
        boilerUnitLiveData.value = list
    }

}