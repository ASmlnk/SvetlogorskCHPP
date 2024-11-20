package com.example.svetlogorskchpp.__data.repository.equipment.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseBigJsonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LightingAndOtherRepositoryImpl @Inject constructor(
    private val dao: LightingAndOtherDao,
    private val firebaseBigJsonRepository: FirebaseBigJsonRepository
): EquipmentRepository<LightingAndOtherEntity>, EquipmentConsumerRepository<LightingAndOtherEntity>, EquipmentUpdateFirebaseRepository {
    override suspend fun saveItemOpenEquipment(itemEntity: LightingAndOtherEntity): SuccessResultFirebase {
        val dataFirebase = firebaseBigJsonRepository.getDocument<LightingAndOtherEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_LIGHTING_AND_OTHER,
            LightingAndOtherEntity::class.java).toMutableList()

        dataFirebase.add(itemEntity)
        val resultInsert = firebaseBigJsonRepository.insertDocument(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_LIGHTING_AND_OTHER)

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = firebaseBigJsonRepository.getDocument<LightingAndOtherEntity>(
                        FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                        FirebaseKey.DOCUMENT_LIGHTING_AND_OTHER,
                        LightingAndOtherEntity::class.java)
                    clearTable()
                    dao.insertAll(newDataFirebase)
                    SuccessResultFirebase.UPDATE_OK
                }  catch (_: Exception) {
                    SuccessResultFirebase.UPDATE_ERROR
                }
            }
        }
    }

    override fun getAllItemEquipment(): Flow<List<LightingAndOtherEntity>?> {
        return dao.getAllItemEntityFlow()
    }

    override fun getItemEquipment(id: String): Flow<LightingAndOtherEntity?> {
        return dao.getItemEntityFlow(id)
    }

    override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
        val listDataFirebase = firebaseBigJsonRepository.getDocument<LightingAndOtherEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TG,
            LightingAndOtherEntity::class.java)
        val listDataLocale = dao.getAllItemEntity()
        if (listDataLocale == listDataFirebase) {
            return@withContext
        } else {
            clearTable()
            dao.insertAll(listDataFirebase)
        }
    }

    override fun getItemEntityConsumerFlow(id: String): Flow<List<LightingAndOtherEntity>> {
        return dao.getItemEntityConsumerFlow(id)
    }

    override suspend fun loadingLocaleInFirebase() {
        val listDataLocale = dao.getAllItemEntity()
         firebaseBigJsonRepository.insertDocument(
            listDataLocale,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_LIGHTING_AND_OTHER)
    }

    suspend fun clearTable() {
        dao.clearTable()
    }

    suspend fun saveAllEquipment(itemEntity: List <LightingAndOtherEntity>) {
        val dataFirebase = firebaseBigJsonRepository.getDocument<LightingAndOtherEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_LIGHTING_AND_OTHER,
            LightingAndOtherEntity::class.java).toMutableList()

        dataFirebase.addAll(itemEntity)
        firebaseBigJsonRepository.insertDocument(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_LIGHTING_AND_OTHER)

        val newDataFirebase = firebaseBigJsonRepository.getDocument<LightingAndOtherEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_LIGHTING_AND_OTHER,
            LightingAndOtherEntity::class.java)

        clearTable()
        dao.insertAll(newDataFirebase)

    }
}