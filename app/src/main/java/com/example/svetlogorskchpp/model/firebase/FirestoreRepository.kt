package com.example.svetlogorskchpp.model.firebase

import android.content.Context
import android.util.Log
import com.example.svetlogorskchpp.SharedPreferencesManager
import com.example.svetlogorskchpp.model.inspectionSchedule.ChecklistInspection
import com.example.svetlogorskchpp.model.ElectricalAssembly
import com.example.svetlogorskchpp.model.ElectricalAssemblyFirebase
import com.example.svetlogorskchpp.model.ItemElectricalAssembly
import com.example.svetlogorskchpp.model.UpdateDateFB
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.example.svetlogorskchpp.model.inspectionSchedule.InSc
import com.example.svetlogorskchpp.model.inspectionSchedule.Inspection
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.GregorianCalendar

class FirestoreRepository private constructor(context: Context) {

    val remoteDB = FirebaseFirestore.getInstance()
    private val _listInspectionScheduleStateFlow = MutableStateFlow<List<Inspection>>(emptyList())
    val listInspectionStateFlow: StateFlow<List<Inspection>>
        get() = _listInspectionScheduleStateFlow

    private val _listChecklistInspectionStateFlow =
        MutableStateFlow<List<ChecklistInspection>>(emptyList())
    val listChecklistInspection: StateFlow<List<ChecklistInspection>>
        get() = _listChecklistInspectionStateFlow

    private val _listValveStateFlow =
        MutableStateFlow<List<ChecklistInspection>>(emptyList())
    val listValveSateFlow: StateFlow<List<ChecklistInspection>>
        get() = _listValveStateFlow


    fun getAll(request: String): CollectionReference {
        remoteDB.disableNetwork()
        return remoteDB.collection(request)
    }

    suspend fun getChecklistInspection(nameCategory: String) {
        withContext(Dispatchers.IO) {
            remoteDB.enableNetwork()
            val list = mutableListOf<ChecklistInspection>()
            val data = remoteDB.collection(InSc.CHECKLIST.get).document(nameCategory).get()
            val listData = data.await().data
            listData?.forEach {
                val nameBlank = it.key
                val map = it.value as Map<String, Map<String, String>>
                map.forEach {
                    val numberChecklist = it.key
                    val nameChecklist = it.value
                    val checklistInspection = ChecklistInspection(
                        nameBlank = nameBlank,
                        numberChecklist = numberChecklist,
                        nameCheck = nameChecklist,
                    )
                    list.add(checklistInspection)
                }
            }
            when(nameCategory) {
                InSc.INSPECTION.get -> _listChecklistInspectionStateFlow.value = list
                InSc.VALVE.get -> _listValveStateFlow.value = list
            }
        }
    }

    fun clearListChecklistInspection() {
        _listChecklistInspectionStateFlow.value = emptyList()
    }

    fun clearListValue() {
        _listValveStateFlow.value = emptyList()
    }

    suspend fun getAllInspection(date: String) {
        withContext(Dispatchers.IO) {
            val isUpdate = getUpdateDateInspectionSchedule()?.let {
                GregorianCalendar()
                val dateUpdateDateFB = GregorianCalendar(
                    it.year.toInt(),
                    it.month.toInt(),
                    it.dayMonth.toInt()
                ).timeInMillis

                val dateCash =
                    SharedPreferencesManager.getString("date_inspection_schedule", "0").toLong()
                dateUpdateDateFB > dateCash
            } ?: false

            if (isUpdate) {
                SharedPreferencesManager.saveString("date_inspection_schedule", date)
                val listData = getAllInspectionScheduleServer()
                withContext(Dispatchers.Main) {
                    _listInspectionScheduleStateFlow.value = listData.sortedBy { it.content }
                }
            } else {
                val listData = getAllInspectionScheduleCache()
                withContext(Dispatchers.Main) {
                    _listInspectionScheduleStateFlow.value = listData.sortedBy { it.content }
                }
            }
        }
    }

    suspend fun getAllInspectionScheduleServer(): List<Inspection> {
        remoteDB.enableNetwork()
        val list = mutableListOf<Inspection>()
        val data = remoteDB.collection(InSc.INSPECTION_SCHEDULE.get).get()
        val listData = data.await()
        for (it in listData) {
            val inspection = it.toObject<Inspection>().apply {
                id = it.id
            }
            list.add(inspection)
        }
        return list
    }

    suspend fun getAllInspectionScheduleCache(): List<Inspection> {
        remoteDB.disableNetwork()
        val list = mutableListOf<Inspection>()
        val data = remoteDB.collection(InSc.INSPECTION_SCHEDULE.get).get()
        val listData = data.await()
        for (it in listData) {
            val inspection = it.toObject<Inspection>().apply { id = it.id }
            list.add(inspection)
        }
        return list
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

    suspend fun getUpdateDateInspectionSchedule(): UpdateDateFB? {
        remoteDB.enableNetwork()
        val data = remoteDB.collection(ElectricMotorType.UPDATE_BD.nameCategoryFirebase)
            .document("updateInspectionSchedule")
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
            val electricalAssemblyFirebase =
                it.toObject<ElectricalAssemblyFirebase>().apply { id = it.id }
            list.add(electricalAssemblyFirebase)
        }
        return list
    }

    suspend fun getAllElectricalAssemblyCache(): List<ElectricalAssemblyFirebase> {

        remoteDB.disableNetwork()
        val list = mutableListOf<ElectricalAssemblyFirebase>()
        val data = remoteDB.collection(ElectricMotorType.ASSEMBLY.nameCategoryFirebase).get()
        val listData = data.await()
        for (it in listData) {
            val electricalAssemblyFirebase =
                it.toObject<ElectricalAssemblyFirebase>().apply { id = it.id }
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