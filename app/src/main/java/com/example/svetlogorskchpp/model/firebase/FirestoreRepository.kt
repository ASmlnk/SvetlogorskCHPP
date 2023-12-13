package com.example.svetlogorskchpp.model.firebase

import android.content.Context
import com.example.svetlogorskchpp.SharedPreferencesManager
import com.example.svetlogorskchpp.model.UpdateDateFB
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.GregorianCalendar

class FirestoreRepository private constructor(context: Context){

    val remoteDB = FirebaseFirestore.getInstance()

    fun getAll(request: String): CollectionReference {
        remoteDB.disableNetwork()
        return remoteDB.collection(request)
    }

    suspend fun getUpdateDateFB(): UpdateDateFB?  {
        remoteDB.enableNetwork()
        val data = remoteDB.collection("ОбновлениеБД")
            .document("updateElectricMotor")
            .get()
        val date = data.await().toObject<UpdateDateFB>()
        return date
    }

    suspend fun getAllElectricMotorServer(): List<ElectricMotor> {

        remoteDB.enableNetwork()
        val list = mutableListOf<ElectricMotor>()

        list.apply {
            addAll(getListCollection(ElectricMotorType.BOILESRS.motorName))
            addAll(getListCollection(ElectricMotorType.TURBOGENERATORS.motorName))
            addAll(getListCollection(ElectricMotorType.ANOTHER.motorName))
        }
        return list.sortedBy { it.name }
    }

    suspend fun getAllElectricMotorCache(): List<ElectricMotor> {
        remoteDB.disableNetwork()
        val list = mutableListOf<ElectricMotor>()

        list.apply {
            addAll(getListCollection(ElectricMotorType.BOILESRS.motorName))
            addAll(getListCollection(ElectricMotorType.TURBOGENERATORS.motorName))
            addAll(getListCollection(ElectricMotorType.ANOTHER.motorName))
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

        fun initialize (context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FirestoreRepository(context)
            }
        }
        fun get(): FirestoreRepository {
            return INSTANCE ?: throw IllegalStateException("FirestoreRepository must be initialized")
        }
    }
}