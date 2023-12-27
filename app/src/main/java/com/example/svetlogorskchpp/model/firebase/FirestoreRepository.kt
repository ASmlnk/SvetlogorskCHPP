package com.example.svetlogorskchpp.model.firebase

import android.content.Context
import com.example.svetlogorskchpp.model.ElectricalAssembly
import com.example.svetlogorskchpp.model.ElectricalAssemblyFirebase
import com.example.svetlogorskchpp.model.ItemElectricalAssembly
import com.example.svetlogorskchpp.model.UpdateDateFB
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class FirestoreRepository private constructor(context: Context) {

    val remoteDB = FirebaseFirestore.getInstance()

    fun getAll(request: String): CollectionReference {
        remoteDB.disableNetwork()
        return remoteDB.collection(request)
    }

    suspend fun getUpdateDateElectricMotor(): UpdateDateFB? {
        remoteDB.enableNetwork()
        val data = remoteDB.collection(ElectricMotorType.UPDATE_BD.nameCategoryFirebase)
            .document("updateElectricMotor")
            .get()
        val date = data.await().toObject<UpdateDateFB>()
        return date
    }

    suspend fun getUpdateDateElectricAssembly(): UpdateDateFB? {
        remoteDB.enableNetwork()
        val data = remoteDB.collection(ElectricMotorType.UPDATE_BD.nameCategoryFirebase)
            .document("updateElectricAssembly")
            .get()
        val date = data.await().toObject<UpdateDateFB>()
        return date
    }

    suspend fun getIdElectricalAssembly(id: String): ElectricalAssembly {
        remoteDB.disableNetwork()
        val data = remoteDB.collection("Сборки").document(id).get()
        val electricalAssemblyFirebase = data.await().toObject<ElectricalAssemblyFirebase>()
        val listItemAssembly = mutableListOf<ItemElectricalAssembly>()
        var electricalAssembly = ElectricalAssembly()
        electricalAssemblyFirebase?.let {
            for ((key, value) in electricalAssemblyFirebase.listItemAssembly) {
                val itemElectricalAssembly = ItemElectricalAssembly(
                    name = key,
                    electricMotor = value
                )
                listItemAssembly.add(itemElectricalAssembly)
            }
            electricalAssembly = ElectricalAssembly(
                id = electricalAssemblyFirebase.id,
                nameAssembly = electricalAssemblyFirebase.nameAssembly,
                nameDepartment = electricalAssemblyFirebase.nameDepartment,
                inputAssembly = electricalAssemblyFirebase.inputAssembly,
                category = electricalAssemblyFirebase.category,
                listItemAssembly = listItemAssembly.toList()
            )
        }
        return electricalAssembly
    }

    suspend fun getAllElectricalAssemblyServer(): List<ElectricalAssemblyFirebase> {

        remoteDB.enableNetwork()
        val list = mutableListOf<ElectricalAssemblyFirebase>()
        val data = remoteDB.collection(ElectricMotorType.ASSEMBLY.nameCategoryFirebase).get()
        val listData = data.await()
        for (it in listData) {
            val electricalAssemblyFirebase = it.toObject<ElectricalAssemblyFirebase>().apply { id = it.id }
            list.add(electricalAssemblyFirebase)
        }
        return list
    }

    suspend fun getAllElectricalAssemblyCache(): List<ElectricalAssemblyFirebase> {

        remoteDB.enableNetwork()
        val list = mutableListOf<ElectricalAssemblyFirebase>()
        val data = remoteDB.collection(ElectricMotorType.ASSEMBLY.nameCategoryFirebase).get()
        val listData = data.await()
        for (it in listData) {
            val electricalAssemblyFirebase = it.toObject<ElectricalAssemblyFirebase>().apply { id = it.id }
            list.add(electricalAssemblyFirebase)
        }
        return list
    }

    suspend fun getAllElectricMotorServer(): List<ElectricMotor> {

        remoteDB.enableNetwork()
        val list = mutableListOf<ElectricMotor>()

        list.apply {
            addAll(getListCollection(ElectricMotorType.BOILESRS.nameCategoryFirebase))
            addAll(getListCollection(ElectricMotorType.TURBOGENERATORS.nameCategoryFirebase))
            addAll(getListCollection(ElectricMotorType.ANOTHER.nameCategoryFirebase))
        }
        return list.sortedBy { it.name }
    }

    suspend fun getAllElectricMotorCache(): List<ElectricMotor> {
        remoteDB.disableNetwork()
        val list = mutableListOf<ElectricMotor>()

        list.apply {
            addAll(getListCollection(ElectricMotorType.BOILESRS.nameCategoryFirebase))
            addAll(getListCollection(ElectricMotorType.TURBOGENERATORS.nameCategoryFirebase))
            addAll(getListCollection(ElectricMotorType.ANOTHER.nameCategoryFirebase))
        }
        return list.sortedBy { it.name }
    }

    private suspend fun getListCollection(nameCollection: String): List<ElectricMotor> {
        val list = mutableListOf<ElectricMotor>()
        val data = remoteDB.collection(nameCollection).get()
        val listData = data.await()
        for (it in listData) {
            list.add(it.toObject<ElectricMotor>().apply { id = it.id })
        }
        return list
    }

    companion object {
        private var INSTANCE: FirestoreRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FirestoreRepository(context)
            }
        }

        fun get(): FirestoreRepository {
            return INSTANCE
                ?: throw IllegalStateException("FirestoreRepository must be initialized")
        }
    }
}