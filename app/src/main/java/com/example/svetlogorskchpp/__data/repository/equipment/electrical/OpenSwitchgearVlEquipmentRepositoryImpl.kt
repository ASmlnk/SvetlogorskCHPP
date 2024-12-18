package com.example.svetlogorskchpp.__data.repository.equipment.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.ReservationSaveFileRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OpenSwitchgearVlEquipmentRepositoryImpl @Inject constructor(
    private val dao: OpenSwitchgearVlDao,
    private val repositoryFirebase: FirebaseRepositoryImpl,
    private val reservationSaveFileRepository: ReservationSaveFileRepository
) : EquipmentRepository<OpenSwitchgearVlEntity>,
    EquipmentUpdateFirebaseRepository {

    override suspend fun saveItemOpenEquipment(itemEntity: OpenSwitchgearVlEntity):  SuccessResultFirebase {
        val dataFirebase = repositoryFirebase.getDocuments<OpenSwitchgearVlEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_ORY,
            OpenSwitchgearVlEntity::class.java).toMutableList()

        val itemId = itemEntity.id
        dataFirebase.removeIf {it.id == itemId}

        dataFirebase.add(itemEntity)
        val resultInsert = repositoryFirebase.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_ORY)

        return when (resultInsert) {
             SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
             SuccessResultFirebase.UPDATE_OK -> {
              try {
                  val newDataFirebase = repositoryFirebase.getDocuments<OpenSwitchgearVlEntity>(
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

    override fun getAllItemEquipment(): Flow<List<OpenSwitchgearVlEntity>?> {
        return dao.getAllItemOpenSwitchgearStream()
    }

    override fun getItemEquipment(id: String): Flow<OpenSwitchgearVlEntity?> {
        return dao.getItemOpenSwitchgear(id)
    }

    private suspend fun clearTable() {
        dao.clearTable()
    }

     override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
         val openSwitchgearVlsFirebase = repositoryFirebase.getDocuments<OpenSwitchgearVlEntity>(
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

    override fun getSearchElectricalEquipment(
        searchQuery: String,
        prefixQuery: String,
    ): Flow<List<OpenSwitchgearVlEntity>> {
        return dao.getSearchItems(searchQuery,prefixQuery)
    }

    override suspend fun loadingLocaleInFirebase() {
        val listDataLocale = dao.getAllItemOpenSwitchgear()
        repositoryFirebase.insertDocuments(
            listDataLocale,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_ORY
        )
    }

    override suspend fun reservationFirebase() {
        val listDataFirebase = repositoryFirebase.getDocuments<OpenSwitchgearVlEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_ORY,
            OpenSwitchgearVlEntity::class.java
        )
        repositoryFirebase.insertDocuments(
            listDataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_ORY_REZ
        )
        reservationSaveFileRepository.saveFile(FirebaseKey.DOCUMENT_ORY, listDataFirebase)
    }
}