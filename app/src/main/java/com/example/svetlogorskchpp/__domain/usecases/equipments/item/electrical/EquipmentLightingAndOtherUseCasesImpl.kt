package com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.LightingAndOtherMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.LightingAndOther
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentLightingAndOtherUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<LightingAndOtherEntity>,
    private val mapper: LightingAndOtherMapper,
    private val networkUseCases: NetworkAvailableUseCase,
) : EquipmentsUseCases<LightingAndOther> {
    override suspend fun saveItemEquipment(item: LightingAndOther): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
            val resultRepository =
                repository.saveItemOpenEquipment(mapper.toLightingAndOtherEntity(item))
            when (resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success(SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemEquipment(): Flow<List<LightingAndOther>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toLightingAndOther(it) } ?: emptyList()
        }
    }

    override suspend fun getItemEquipment(id: String): Flow<LightingAndOther?> {
        return repository.getItemEquipment(id).map { entities ->
            entities?.let { mapper.toLightingAndOther(entities) }
        }
    }
}