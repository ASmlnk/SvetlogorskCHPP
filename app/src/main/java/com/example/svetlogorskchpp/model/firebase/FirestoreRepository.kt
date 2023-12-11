package com.example.svetlogorskchpp.model.firebase

import android.content.Context
import com.example.svetlogorskchpp.model.UpdateDateFB
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirestoreRepository private constructor(context: Context){

    val remoteDB = FirebaseFirestore.getInstance()

    fun getAll(request: String): CollectionReference {
        remoteDB.disableNetwork()
        return remoteDB.collection(request)
    }

    suspend fun getUpdateDateFB(): UpdateDateFB? = withContext(Dispatchers.IO) {
        remoteDB.enableNetwork()
        val data = remoteDB.collection("ОбновлениеБД").document("updateElectricMotor").get()
        val date = data.await().toObject<UpdateDateFB>()
        return@withContext date
    }

    suspend fun getAllElectricMotorServer(): List<ElectricMotor> = withContext(Dispatchers.IO) {

        remoteDB.enableNetwork()
        val list = mutableListOf<ElectricMotor>()

        list.apply {
            addAll(getListCollection("Котлоагрегаты"))
            addAll(getListCollection("Турбогенераторы"))
            addAll(getListCollection("Остальное"))
        }
        return@withContext list.sortedBy { it.name }
    }

    suspend fun getAllElectricMotorCache(): List<ElectricMotor> = withContext(Dispatchers.IO) {

        remoteDB.disableNetwork()
        val list = mutableListOf<ElectricMotor>()

        list.apply {
            addAll(getListCollection("Котлоагрегаты"))
            addAll(getListCollection("Турбогенераторы"))
            addAll(getListCollection("Остальное"))
        }
        return@withContext list.sortedBy { it.name }
    }

    private suspend fun getListCollection(nameCollection: String): List<ElectricMotor> =
        withContext(Dispatchers.IO) {

            val list = mutableListOf<ElectricMotor>()
            val data = remoteDB.collection(nameCollection).get()
            val listData = data.await()
            for (it in listData) {
                list.add(it.toObject<ElectricMotor>().apply { id = it.id })
            }
            return@withContext list
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