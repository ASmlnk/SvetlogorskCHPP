package com.example.svetlogorskchpp.__data.repository.equipment.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentElMotorChapterRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentItemDeleteRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.ReservationSaveFileRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseBigJsonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ElMotorRepositoryImpl @Inject constructor(
    private val dao: ElMotorDao,
    private val firebaseBigJsonRepository: FirebaseBigJsonRepository,
    private val reservationSaveFileRepository: ReservationSaveFileRepository
) : EquipmentRepository<ElMotorEntity>, EquipmentConsumerRepository<ElMotorEntity>,
    EquipmentUpdateFirebaseRepository, EquipmentItemDeleteRepository, EquipmentElMotorChapterRepository {

        override suspend fun saveItemOpenEquipment(itemEntity: ElMotorEntity): SuccessResultFirebase {
        val dataFirebase = firebaseBigJsonRepository.getDocuments<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java
        ).toMutableList()

        val itemId = itemEntity.id
        dataFirebase.removeIf {it.id == itemId}

        dataFirebase.add(itemEntity)
        val resultInsert = firebaseBigJsonRepository.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR
        )

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = firebaseBigJsonRepository.getDocuments<ElMotorEntity>(
                        FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                        FirebaseKey.DOCUMENT_EL_MOTOR,
                        ElMotorEntity::class.java
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

    override fun getAllItemEquipment(): Flow<List<ElMotorEntity>?> {
        return dao.getAllItemEntityFlow()
    }

    override fun getItemEquipment(id: String): Flow<ElMotorEntity?> {
        return dao.getItemEntityFlow(id)
    }

    override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
        val listDataFirebase = firebaseBigJsonRepository.getDocuments<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java
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
    ): Flow<List<ElMotorEntity>> {
        return dao.getSearchItems(searchQuery,prefixQuery)
    }

    override fun getItemEntityConsumerFlow(id: String): Flow<List<ElMotorEntity>> {
        return dao.getItemEntityConsumerFlow(id)
    }

    override suspend fun loadingLocaleInFirebase() {
        val listDataLocale = dao.getAllItemEntity()
        firebaseBigJsonRepository.insertDocuments(
            listDataLocale,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR
        )
    }

    override suspend fun reservationFirebase() {
        val listDataFirebase = firebaseBigJsonRepository.getDocuments<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java
        )
        firebaseBigJsonRepository.insertDocuments(
            listDataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR_REZ
        )
        reservationSaveFileRepository.saveFile(FirebaseKey.DOCUMENT_EL_MOTOR, listDataFirebase)

    }

    suspend fun clearTable() {
        dao.clearTable()
    }

    suspend fun saveAllEquipment(itemEntity: List<ElMotorEntity>) {
        val dataFirebase = firebaseBigJsonRepository.getDocuments<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java
        ).toMutableList()

        dataFirebase.addAll(itemEntity)
        firebaseBigJsonRepository.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR
        )

        val newDataFirebase = firebaseBigJsonRepository.getDocuments<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java
        )

        clearTable()
        dao.insertAll(newDataFirebase)

    }

    override suspend fun deleteItem(id: String): SuccessResultFirebase {
        val dataFirebase = firebaseBigJsonRepository.getDocuments<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java
        ).toMutableList()

        dataFirebase.removeIf { it.id == id }
        val resultInsert = firebaseBigJsonRepository.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR
        )

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = firebaseBigJsonRepository.getDocuments<ElMotorEntity>(
                        FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                        FirebaseKey.DOCUMENT_EL_MOTOR,
                        ElMotorEntity::class.java
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

    override fun getIsRep(): Flow<List<ElMotorEntity>?> {
        return dao.getIsRep()
    }

    override fun getElMotorGenCategory(generalCategory: String): Flow<List<ElMotorEntity>?> {
        return dao.getElMotorGenCategory(generalCategory)
    }
}