package com.example.svetlogorskchpp.__data.repository.electrical_equipment.open_switchgear

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OpenSwitchgearVlRepositoryImpl @Inject constructor(
    private val dao: OpenSwitchgearVlDao,
    private val repositoryFirebase: FirebaseRepository
) : OpenSwitchgearRepository<OpenSwitchgearVlEntity> {

    override suspend fun saveItemOpenSwitchgear(openSwitchgearVlEntity: OpenSwitchgearVlEntity):  SuccessResultFirebase {
        val dataFirebase = repositoryFirebase.getDocument<OpenSwitchgearVlEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_ORY,
            OpenSwitchgearVlEntity::class.java).toMutableList()

        dataFirebase.add(openSwitchgearVlEntity)
        val resultInsert = repositoryFirebase.insertDocument(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_ORY)

        return when (resultInsert) {
             SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
             SuccessResultFirebase.UPDATE_OK -> {
              try {
                  val newDataFirebase = repositoryFirebase.getDocument<OpenSwitchgearVlEntity>(
                      FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                      FirebaseKey.DOCUMENT_ORY,
                      OpenSwitchgearVlEntity::class.java)
                  clearTable()
                  dao.insertAll(newDataFirebase)
                 SuccessResultFirebase.UPDATE_OK
              }  catch (_: Exception) {
                  SuccessResultFirebase.UPDATE_ERROR
              }
            }
        }
    }

    override fun getAllItemOpenSwitchgear(): Flow<List<OpenSwitchgearVlEntity>?> {
        return dao.getAllItemOpenSwitchgearStream()
    }

    override fun getItemOpenSwitchgear(id: String): Flow<OpenSwitchgearVlEntity?> {
        return dao.getItemOpenSwitchgear(id)
    }

    private suspend fun clearTable() {
        dao.clearTable()
    }

     override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
         val openSwitchgearVlsFirebase = repositoryFirebase.getDocument<OpenSwitchgearVlEntity>(
             FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
             FirebaseKey.DOCUMENT_ORY,
             OpenSwitchgearVlEntity::class.java)
         val openSwitchgearVlsLocale = dao.getAllItemOpenSwitchgear()
         if (openSwitchgearVlsFirebase == openSwitchgearVlsLocale) {
             return@withContext
         } else {
             clearTable()
             dao.insertAll(openSwitchgearVlsFirebase)
         }
    }
}