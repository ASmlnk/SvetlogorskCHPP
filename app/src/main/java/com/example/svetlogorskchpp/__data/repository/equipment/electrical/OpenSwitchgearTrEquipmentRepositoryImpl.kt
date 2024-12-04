package com.example.svetlogorskchpp.__data.repository.equipment.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.ReservationSaveFileRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OpenSwitchgearTrEquipmentRepositoryImpl @Inject constructor(
    private val dao: OpenSwitchgearTrDao,
    private val repositoryFirebase: FirebaseRepositoryImpl,
    private val reservationSaveFileRepository: ReservationSaveFileRepository
) : EquipmentRepository<OpenSwitchgearTrEntity>,
    EquipmentUpdateFirebaseRepository {

    override suspend fun saveItemOpenEquipment(itemEntity: OpenSwitchgearTrEntity):  SuccessResultFirebase {
        val dataFirebase = repositoryFirebase.getDocuments<OpenSwitchgearTrEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TR,
            OpenSwitchgearTrEntity::class.java).toMutableList()

        val itemId = itemEntity.id
        dataFirebase.removeIf {it.id == itemId}

        dataFirebase.add(itemEntity)
        val resultInsert = repositoryFirebase.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TR)

        return when (resultInsert) {
             SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
             SuccessResultFirebase.UPDATE_OK -> {
              try {
                  val newDataFirebase = repositoryFirebase.getDocuments<OpenSwitchgearTrEntity>(
                      FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                      FirebaseKey.DOCUMENT_TR,
                      OpenSwitchgearTrEntity::class.java)
                  clearTable()
                  dao.insertAll(newDataFirebase)
                 SuccessResultFirebase.UPDATE_OK
              }  catch (_: Exception) {
                  SuccessResultFirebase.UPDATE_ERROR
              }
            }
        }
    }

    override fun getAllItemEquipment(): Flow<List<OpenSwitchgearTrEntity>?> {
        return dao.getAllItemOpenSwitchgearStream()
    }

    override fun getItemEquipment(id: String): Flow<OpenSwitchgearTrEntity?> {
        return dao.getItemOpenSwitchgear(id)
    }

    private suspend fun clearTable() {
        dao.clearTable()
    }

     override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
         val openSwitchgearTrsFirebase = repositoryFirebase.getDocuments<OpenSwitchgearTrEntity>(
             FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
             FirebaseKey.DOCUMENT_TR,
             OpenSwitchgearTrEntity::class.java)
         val openSwitchgearVlsLocale = dao.getAllItemOpenSwitchgear()
         if (openSwitchgearTrsFirebase == openSwitchgearVlsLocale) {
             return@withContext
         } else {
             clearTable()
             dao.insertAll(openSwitchgearTrsFirebase)
         }
    }

    override fun getSearchElectricalEquipment(
        searchQuery: String,
        prefixQuery: String,
    ): Flow<List<OpenSwitchgearTrEntity>> {
        return dao.getSearchItems(searchQuery,prefixQuery)
    }

    override suspend fun loadingLocaleInFirebase() {
        val listDataLocale = dao.getAllItemOpenSwitchgear()
        repositoryFirebase.insertDocuments(
            listDataLocale,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TR
        )
    }

    override suspend fun reservationFirebase() {
        val listDataFirebase = repositoryFirebase.getDocuments<OpenSwitchgearTrEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TR,
            OpenSwitchgearTrEntity::class.java
        )
        repositoryFirebase.insertDocuments(
            listDataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TR_REZ
        )
        reservationSaveFileRepository.saveFile(FirebaseKey.DOCUMENT_TR, listDataFirebase)
    }
}