package com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.TransformerOwnNeedsMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TransformerOwnNeeds
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentTransformerOwnNeedsUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<TransformerOwnNeedsEntity>,
    private val mapper: TransformerOwnNeedsMapper,
    private val networkUseCases: NetworkAvailableUseCase,
) : EquipmentsUseCases<TransformerOwnNeeds> {
    override suspend fun saveItemEquipment(item: TransformerOwnNeeds): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
            val resultRepository =
                repository.saveItemOpenEquipment(mapper.toTransformerOwnNeedsEntity(item))
            when (resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success(SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemEquipment(): Flow<List<TransformerOwnNeeds>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toTransformerOwnNeeds(it) } ?: emptyList()
        }
    }

    override suspend fun getItemEquipment(id: String): Flow<TransformerOwnNeeds?> {
        return repository.getItemEquipment(id).map { openSwitchgearVlEntity ->
            openSwitchgearVlEntity?.let { mapper.toTransformerOwnNeeds(openSwitchgearVlEntity) }
        }
    }
}