package com.example.svetlogorskchpp.__data.repository.equipment.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseBigJsonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ElMotorRepositoryImpl @Inject constructor(
    private val dao: ElMotorDao,
    private val firebaseBigJsonRepository: FirebaseBigJsonRepository
): EquipmentRepository<ElMotorEntity>, EquipmentConsumerRepository<ElMotorEntity>, EquipmentUpdateFirebaseRepository {
    override suspend fun saveItemOpenEquipment(itemEntity: ElMotorEntity): SuccessResultFirebase {
        val dataFirebase = firebaseBigJsonRepository.getDocument<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java).toMutableList()

        dataFirebase.add(itemEntity)
        val resultInsert = firebaseBigJsonRepository.insertDocument(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR)

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = firebaseBigJsonRepository.getDocument<ElMotorEntity>(
                        FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                        FirebaseKey.DOCUMENT_EL_MOTOR,
                        ElMotorEntity::class.java)
                    clearTable()
                    dao.insertAll(newDataFirebase)
                    SuccessResultFirebase.UPDATE_OK
                }  catch (_: Exception) {
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
        val listDataFirebase = firebaseBigJsonRepository.getDocument<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java)
        val listDataLocale = dao.getAllItemEntity()
        if (listDataLocale == listDataFirebase) {
            return@withContext
        } else {
            clearTable()
            dao.insertAll(listDataFirebase)
        }
    }

    override fun getItemEntityConsumerFlow(id: String): Flow<List<ElMotorEntity>> {
        return dao.getItemEntityConsumerFlow(id)
    }

    override suspend fun loadingLocaleInFirebase() {
        val listDataLocale = dao.getAllItemEntity()
        firebaseBigJsonRepository.insertDocument(
            listDataLocale,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR)
    }

    suspend fun clearTable() {
        dao.clearTable()
    }

    suspend fun saveAllEquipment(itemEntity: List <ElMotorEntity>) {
        val dataFirebase = firebaseBigJsonRepository.getDocument<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java).toMutableList()

        dataFirebase.addAll(itemEntity)
        firebaseBigJsonRepository.insertDocument(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR)

        val newDataFirebase = firebaseBigJsonRepository.getDocument<ElMotorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_EL_MOTOR,
            ElMotorEntity::class.java)

        clearTable()
        dao.insertAll(newDataFirebase)

    }
}