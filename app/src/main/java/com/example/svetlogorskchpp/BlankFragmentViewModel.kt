package com.example.svetlogorskchpp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BlankFragmentViewModel : ViewModel() {

    val data = FirestoreRepository.get()
    val listData = mutableListOf<ElectricMotor>()
    val liveData = MutableLiveData<Int>()
    val listDataFilter = mutableListOf<ElectricMotor>()

     fun get() {
        val dataKa = data.getAll("Остальное").get()
         viewModelScope.launch {
             val listKa = dataKa.await()
             for (it in listKa) {
                 listData.add(it.toObject<ElectricMotor>().apply { id = it.id })
             }
             /*listDataFilter.apply {
                 addAll(listData.filter { it.voltage == "6000" })
             }*/
             liveData.value = listData.size

         }
    }

    fun set() {

        listData.forEach {
           // val dataKa = data.getAll("Остальное").get()
            val p = it.characteristics.split("\\n").first()
            val schema = it.characteristics.split("\\n").last()
            val i = it.characteristics.split("\\n")[2]

            val dataEM = HashMap<String, Any>()
            dataEM["schema"] = schema
            dataEM["p"] = p
            dataEM["i"] = i

            data.remoteDB.collection("Остальное").document(it.id)
                .set(dataEM, SetOptions.merge())
        }
    }

    fun setNewElement(nameChapter: String, dataElement: HashMap<String, Any>) {
        data.remoteDB.collection("$nameChapter")
            .add(dataElement)
    }
}