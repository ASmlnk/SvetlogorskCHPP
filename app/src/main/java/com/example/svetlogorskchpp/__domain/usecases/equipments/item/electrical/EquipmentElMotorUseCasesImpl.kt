package com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElMotorMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.ElMotor
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentElMotorUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<ElMotorEntity>,
    private val mapper: ElMotorMapper,
    private val networkUseCases: NetworkAvailableUseCase,
) : EquipmentsUseCases<ElMotor> {
    override suspend fun saveItemEquipment(item: ElMotor): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
            val resultRepository =
                repository.saveItemOpenEquipment(mapper.toElMotorEntity(item))
            when (resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success(SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemEquipment(): Flow<List<ElMotor>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toElMotor(it) } ?: emptyList()
        }
    }

    override suspend fun getItemEquipment(id: String): Flow<ElMotor?> {
        return repository.getItemEquipment(id).map { entities ->
            entities?.let { mapper.toElMotor(entities) }
        }
    }

}