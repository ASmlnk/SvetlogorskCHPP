package com.example.svetlogorskchpp.__data.repository.equipment.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentItemDeleteRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseBigJsonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SwitchgearRepositoryImpl @Inject constructor(
    private val dao: SwitchgearDao,
    private val repositoryBigJsonRepository: FirebaseBigJsonRepository,
) : EquipmentRepository<SwitchgearEntity>, EquipmentConsumerRepository<SwitchgearEntity>,
    EquipmentUpdateFirebaseRepository, EquipmentItemDeleteRepository {
    override suspend fun saveItemOpenEquipment(itemEntity: SwitchgearEntity): SuccessResultFirebase {
        val dataFirebase = repositoryBigJsonRepository.getDocument<SwitchgearEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR,
            SwitchgearEntity::class.java
        ).toMutableList()

        val itemId = itemEntity.id
        dataFirebase.removeIf {it.id == itemId}

        dataFirebase.add(itemEntity)
        val resultInsert = repositoryBigJsonRepository.insertDocument(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR
        )

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = repositoryBigJsonRepository.getDocument<SwitchgearEntity>(
                        FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                        FirebaseKey.DOCUMENT_SWITCHGEAR,
                        SwitchgearEntity::class.java
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

    override fun getAllItemEquipment(): Flow<List<SwitchgearEntity>?> {
        return dao.getAllItemEntityFlow()
    }

    override fun getItemEquipment(id: String): Flow<SwitchgearEntity?> {
        return dao.getItemEntityFlow(id)
    }

    override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
        val listDataFirebase = repositoryBigJsonRepository.getDocument<SwitchgearEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR,
            SwitchgearEntity::class.java
        )
        val listDataLocale = dao.getAllItemEntity()
        if (listDataLocale == listDataFirebase) {
            return@withContext
        } else {
            clearTable()
            dao.insertAll(listDataFirebase)
        }
    }

    override fun getItemEntityConsumerFlow(id: String): Flow<List<SwitchgearEntity>> {
        return dao.getItemEntityConsumerFlow(id)
    }

    override suspend fun loadingLocaleInFirebase() {
        val listDataLocale = dao.getAllItemEntity()
        repositoryBigJsonRepository.insertDocument(
            listDataLocale,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR
        )
    }

    suspend fun clearTable() {
        dao.clearTable()
    }

    suspend fun saveAllEquipment(itemEntity: List<SwitchgearEntity>) {
        val dataFirebase = repositoryBigJsonRepository.getDocument<SwitchgearEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR,
            SwitchgearEntity::class.java
        ).toMutableList()

        dataFirebase.addAll(itemEntity)
        repositoryBigJsonRepository.insertDocument(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR
        )

        val newDataFirebase = repositoryBigJsonRepository.getDocument<SwitchgearEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR,
            SwitchgearEntity::class.java
        )

        clearTable()
        dao.insertAll(newDataFirebase)

    }

    suspend fun getAll(): List<SwitchgearEntity> {
        return dao.getAllItemEntity()
    }

    override suspend fun deleteItem(id: String): SuccessResultFirebase {

        val dataFirebase = repositoryBigJsonRepository.getDocument<SwitchgearEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR,
            SwitchgearEntity::class.java
        ).toMutableList()
        dataFirebase.removeIf { it.id == id }

        val resultInsert = repositoryBigJsonRepository.insertDocument(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_SWITCHGEAR
        )

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = repositoryBigJsonRepository.getDocument<SwitchgearEntity>(
                        FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                        FirebaseKey.DOCUMENT_SWITCHGEAR,
                        SwitchgearEntity::class.java
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
}