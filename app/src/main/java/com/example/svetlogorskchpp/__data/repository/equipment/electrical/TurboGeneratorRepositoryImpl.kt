package com.example.svetlogorskchpp.__data.repository.equipment.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__data.model.FirebaseKey
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.firebase.FirebaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TurboGeneratorRepositoryImpl @Inject constructor(
    private val dao: TurboGeneratorDao,
    private val repositoryFirebase: FirebaseRepositoryImpl
) : EquipmentRepository<TurboGeneratorEntity>, EquipmentConsumerRepository<TurboGeneratorEntity> {

    override suspend fun saveItemOpenEquipment(itemEntity: TurboGeneratorEntity): SuccessResultFirebase {
        val dataFirebase = repositoryFirebase.getDocuments<TurboGeneratorEntity>(
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TG,
            TurboGeneratorEntity::class.java).toMutableList()

        val itemId = itemEntity.id
        dataFirebase.removeIf {it.id == itemId}
        dataFirebase.add(itemEntity)
        val resultInsert = repositoryFirebase.insertDocuments(
            dataFirebase,
            FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
            FirebaseKey.DOCUMENT_TG)

        return when (resultInsert) {
            SuccessResultFirebase.UPDATE_ERROR -> SuccessResultFirebase.UPDATE_ERROR
            SuccessResultFirebase.UPDATE_OK -> {
                try {
                    val newDataFirebase = repositoryFirebase.getDocuments<TurboGeneratorEntity>(
                        FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
                        FirebaseKey.DOCUMENT_TG,
                        TurboGeneratorEntity::class.java)
                    clearTable()
                    dao.insertAll(newDataFirebase)
                    SuccessResultFirebase.UPDATE_OK
                }  catch (_: Exception) {
                    SuccessResultFirebase.UPDATE_ERROR
                }
            }
        }
    }

    override fun getAllItemEquipment(): Flow<List<TurboGeneratorEntity>?> {
        return dao.getAllItemEntityFlow()
    }

    override fun getItemEquipment(id: String): Flow<TurboGeneratorEntity?> {
        return dao.getItemEntityFlow(id)
    }

  override suspend fun updateLocaleData() = withContext(Dispatchers.IO) {
      val listDataFirebase = repositoryFirebase.getDocuments<TurboGeneratorEntity>(
          FirebaseKey.COLLECTION_ELECTRICAL_EQUIPMENT,
          FirebaseKey.DOCUMENT_TG,
          TurboGeneratorEntity::class.java)
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
    ): Flow<List<TurboGeneratorEntity>> {
        return dao.getSearchItems(searchQuery,prefixQuery)
    }

    suspend fun clearTable() {
        dao.clearTable()
    }

    override fun getItemEntityConsumerFlow(id: String): Flow<List<TurboGeneratorEntity>> {
        return dao.getItemEntityConsumerFlow(id)
    }
}