package com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.TurbogeneratorMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TurboGenerator
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentTurboGeneratorUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<TurboGeneratorEntity>,
    private val mapper: TurbogeneratorMapper,
    private val networkUseCases: NetworkAvailableUseCase,
): EquipmentsUseCases<TurboGenerator> {
    override suspend fun saveItemEquipment(item: TurboGenerator): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
            val resultRepository =
                repository.saveItemOpenEquipment(mapper.toTurbogeneratorEntity(item))
            when (resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success(SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemEquipment(): Flow<List<TurboGenerator>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toTurbogenerator(it) } ?: emptyList()
        }
    }

    override suspend fun getItemEquipment(id: String): Flow<TurboGenerator?> {
        return repository.getItemEquipment(id).map { entity ->
            entity?.let { mapper.toTurbogenerator(entity) }
        }
    }
}