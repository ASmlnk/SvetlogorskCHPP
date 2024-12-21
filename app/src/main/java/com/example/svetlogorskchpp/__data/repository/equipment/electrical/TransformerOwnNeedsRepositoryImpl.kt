package com.example.svetlogorskchpp.__data.repository.equipment.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentUpdateFirebaseRepository
import com.example.svetlogorskchpp.__data.repository.equipment.ReservationSaveFileRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransformerOwnNeedsRepositoryImpl @Inject constructor(
    private val dao: TransformerOwnNeedsDao,
    private val repositoryFirebase: FirebaseRepositoryImpl,
    private val reservationSaveFileRepository: ReservationSaveFileRepository,
) : EquipmentRepository<TransformerOwnNeedsEntity>,
    EquipmentConsumerRepository<TransformerOwnNeedsEntity>,
    EquipmentUpdateFirebaseRepository {
    override suspend fun saveItemOpenEquipment(itemEntity: TransformerOwnNeedsEntity): SuccessResultFirebase {
        val dataFirebase = repositoryFirebase.getDocuments<TransformerOwnNeedsEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TSN,
            TransformerOwnNeedsEntity::class.java
        ).toMutableList()

        val itemId = itemEntity.id
        dataFirebase.removeIf { it.id == itemId }

        dataFirebase.add(itemEntity)
        val resultInsert = repositoryFirebase.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TSN
        )

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase =
                        repositoryFirebase.getDocuments<TransformerOwnNeedsEntity>(
                            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                            FirebaseKey.DOCUMENT_TSN,
                            TransformerOwnNeedsEntity::class.java
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

    override fun getAllItemEquipment(): Flow<List<TransformerOwnNeedsEntity>?> {
        return dao.getAllItemTransformerOwnNeedsFlow()
    }

    override fun getItemEquipment(id: String): Flow<TransformerOwnNeedsEntity?> {
        return dao.getItemTransformerOwnNeedsFlow(id)
    }

    override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
        val listDataFirebase = repositoryFirebase.getDocuments<TransformerOwnNeedsEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TSN,
            TransformerOwnNeedsEntity::class.java
        )
        val listDataLocale = dao.getAllItemTransformerOwnNeeds()
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
    ): Flow<List<TransformerOwnNeedsEntity>> {
        return dao.getSearchItems(searchQuery, prefixQuery)
    }

    private suspend fun clearTable() {
        dao.clearTable()
    }

    override fun getItemEntityConsumerFlow(id: String): Flow<List<TransformerOwnNeedsEntity>> {
        return dao.getItemTransformerOwnNeedsConsumerFlow(id)
    }

    override suspend fun loadingLocaleInFirebase() {
        val listDataLocale = dao.getAllItemTransformerOwnNeeds()
        repositoryFirebase.insertDocuments(
            listDataLocale,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TSN
        )
    }

    override suspend fun reservationFirebase() {
        val listDataFirebase = repositoryFirebase.getDocuments<TransformerOwnNeedsEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TSN,
            TransformerOwnNeedsEntity::class.java
        )
        repositoryFirebase.insertDocuments(
            listDataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TSN_REZ
        )
        reservationSaveFileRepository.saveFile(FirebaseKey.DOCUMENT_TSN, listDataFirebase)
    }
}