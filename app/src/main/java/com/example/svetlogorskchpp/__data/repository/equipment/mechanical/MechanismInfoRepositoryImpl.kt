package com.example.svetlogorskchpp.__data.repository.equipment.mechanical

import com.example.svetlogorskchpp.__data.database.equipment.mechanism_info.MechanismInfoDao
import com.example.svetlogorskchpp.__data.database.equipment.mechanism_info.MechanismInfoEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentItemDeleteRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentSubMechanismRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentUpdateFirebaseRepository
import com.example.svetlogorskchpp.__data.repository.equipment.ReservationSaveFileRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MechanismInfoRepositoryImpl @Inject constructor(
    private val dao: MechanismInfoDao,
    private val firebaseJsonRepository: FirebaseRepositoryImpl,
    private val reservationSaveFileRepository: ReservationSaveFileRepository,
) : EquipmentRepository<MechanismInfoEntity>, EquipmentUpdateFirebaseRepository,
    EquipmentItemDeleteRepository, EquipmentSubMechanismRepository<MechanismInfoEntity> {

    override suspend fun saveItemOpenEquipment(itemEntity: MechanismInfoEntity): SuccessResultFirebase {
        val dataFirebase = firebaseJsonRepository.getDocuments<MechanismInfoEntity>(
            FirebaseKey.COLLECTION_EQUIPMENT,
            FirebaseKey.DOCUMENT_MECHANISM_INFO,
            MechanismInfoEntity::class.java
        ).toMutableList()

        val itemId = itemEntity.id
        dataFirebase.removeIf { it.id == itemId }

        dataFirebase.add(itemEntity)
        val resultInsert = firebaseJsonRepository.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_EQUIPMENT,
            FirebaseKey.DOCUMENT_MECHANISM_INFO
        )

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = firebaseJsonRepository.getDocuments<MechanismInfoEntity>(
                        FirebaseKey.COLLECTION_EQUIPMENT,
                        FirebaseKey.DOCUMENT_MECHANISM_INFO,
                        MechanismInfoEntity::class.java
                    )
                    clearTable()
                    dao.insertAll(newDataFirebase)
                    SuccessResultFirebase.UPDATE_OK
                } catch (_: Exception) {
                    SuccessResultFirebase.UPDATE_ERROR
                }
            }
        }
    }

    override fun getAllItemEquipment(): Flow<List<MechanismInfoEntity>?> {
        return dao.getAllItemEntityFlow()
    }

    override fun getItemEquipment(id: String): Flow<MechanismInfoEntity?> {
        return dao.getItemEntityFlow(id)
    }

    override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
        val listDataFirebase = firebaseJsonRepository.getDocuments<MechanismInfoEntity>(
            FirebaseKey.COLLECTION_EQUIPMENT,
            FirebaseKey.DOCUMENT_MECHANISM_INFO,
            MechanismInfoEntity::class.java
        )
        val listDataLocale = dao.getAllItemEntity()
        if (listDataLocale == listDataFirebase) {
            return@withContext
        } else {
            clearTable()
            dao.insertAll(listDataFirebase)
        }
    }

    override fun getSearchElectricalEquipment(
        searchQuery: String,
        prefixQuery: String,
    ): Flow<List<MechanismInfoEntity>> {
        return dao.getSearchItems(searchQuery, prefixQuery)
    }

    override suspend fun loadingLocaleInFirebase() {
        val listDataLocale = dao.getAllItemEntity()
        firebaseJsonRepository.insertDocuments(
            listDataLocale,
            FirebaseKey.COLLECTION_EQUIPMENT,
            FirebaseKey.DOCUMENT_MECHANISM_INFO,
        )
    }

    override suspend fun reservationFirebase() {
        val listDataFirebase = firebaseJsonRepository.getDocuments<MechanismInfoEntity>(
            FirebaseKey.COLLECTION_EQUIPMENT,
            FirebaseKey.DOCUMENT_MECHANISM_INFO,
            MechanismInfoEntity::class.java
        )
        firebaseJsonRepository.insertDocuments(
            listDataFirebase,
            FirebaseKey.COLLECTION_EQUIPMENT,
            FirebaseKey.DOCUMENT_MECHANISM_INFO,
        )
        reservationSaveFileRepository.saveFile(
            FirebaseKey.DOCUMENT_MECHANISM_INFO_REZ,
            listDataFirebase
        )
    }

    override suspend fun deleteItem(id: String): SuccessResultFirebase {
        val dataFirebase = firebaseJsonRepository.getDocuments<MechanismInfoEntity>(
            FirebaseKey.COLLECTION_EQUIPMENT,
            FirebaseKey.DOCUMENT_MECHANISM_INFO,
            MechanismInfoEntity::class.java
        ).toMutableList()

        dataFirebase.removeIf { it.id == id }
        val resultInsert = firebaseJsonRepository.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_EQUIPMENT,
            FirebaseKey.DOCUMENT_MECHANISM_INFO
        )

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = firebaseJsonRepository.getDocuments<MechanismInfoEntity>(
                        FirebaseKey.COLLECTION_EQUIPMENT,
                        FirebaseKey.DOCUMENT_MECHANISM_INFO,
                        MechanismInfoEntity::class.java
                    )
                    clearTable()
                    dao.insertAll(newDataFirebase)
                    SuccessResultFirebase.UPDATE_OK
                } catch (_: Exception) {
                    SuccessResultFirebase.UPDATE_ERROR
                }
            }
        }
    }

    suspend fun clearTable() {
        dao.clearTable()
    }

    override fun getItemsEntitySubMechanismFlow(id: String): Flow<List<MechanismInfoEntity>> {
       return dao.getItemEntityEnteredFlow(id)
    }
}