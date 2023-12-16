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

    fun setNewEm() {



        val em104 = ElectricMotor(
            name = "Эксгаустер ТГ-1",
            characteristics = "P = 0,55 кВт\nU = 380 В\nI = 1,61 А\nАИМ-71А-4У2\nЗв\nn = 1350 об/мин",
            category = "ТГ-1",
            schema = "Сб. №2 ТГ-1",
            voltage = "380",
            i = "I = 1,61 А",
            p = "P = 0,55 кВт ",
            generalCategory = "ТГ",
            indexSection = "Р-",
            pump = ""
        )

        val em106 = ElectricMotor(
            name = "Эксгаустер 3А,Б",
            characteristics = "P = 1,5 кВт\nU = 380 В\nI = 3,3 А\nАИМ-71-А4\nЗв\nn = 2850 об/мин",
            category = "ТГ-3",
            schema = "Сб. тир. возб. ТГ-3",
            voltage = "380",
            i = "I = 3,3 А",
            p = "P = 1,5 кВт ",
            generalCategory = "ТГ",
            indexSection = "Р-",
            pump = ""
        )
        val em107 = ElectricMotor(
            name = "Эксгаустер ТГ4",
            characteristics = "P = 0,55 кВт\nU = 380 В\nI = 1,3 А\nАИМ-63-В242\nЗв\nn = 2700 об/мин",
            category = "ТГ-4",
            schema = "Сб. №2 ТГ-4",
            voltage = "380",
            i = "I = 1,3 А",
            p = "P = 0,55 кВт ",
            generalCategory = "ТГ",
            indexSection = "Р-",
            pump = ""
        )
        val em108 = ElectricMotor(
            name = "Эксгаутер 5А, 5Б",
            characteristics = "P = 1,5 кВт\nU = 380 В\nI = 3,3 А\nАИР80А2У3\nЗв\nn = 2950 об/мин",
            category = "ТГ-5",
            schema = "Сб тир. возб. ТГ-3",
            voltage = "380",
            i = "I = 3,3 А",
            p = "P = 1,5 кВт ",
            generalCategory = "ТГ",
            indexSection = "Р-",
            pump = ""
        )


        val dataList = listOf<ElectricMotor>(

            em104,
            em106,
            em107,
            em108

        )
        dataList.forEach {
            val dataElement = HashMap<String, Any>()
            dataElement["name"] = it.name
            dataElement["category"] = it.category
            dataElement["schemaState"] = false
            dataElement["characteristics"] = it.characteristics
            dataElement["rep"] = false
            dataElement["responsibly"] = false
            dataElement["voltage"] = it.voltage
            dataElement["generalCategory"] = it.generalCategory
            dataElement["indexSection"] = it.indexSection
            dataElement["schema"] = it.schema
            dataElement["p"] = it.p
            dataElement["i"] = it.i
            dataElement["pump"] = it.pump

            data.remoteDB.collection("Турбогенераторы")
                .add(dataElement)

        }


    }


    fun setNewElement(nameChapter: String, dataElement: HashMap<String, Any>) {
        data.remoteDB.collection("$nameChapter")
            .add(dataElement)
    }
}