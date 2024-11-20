package com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.SwitchgearMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.Switchgear
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentSwitchgearUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<SwitchgearEntity>,
    private val mapper: SwitchgearMapper,
    private val networkUseCases: NetworkAvailableUseCase,
) : EquipmentsUseCases<Switchgear> {
    override suspend fun saveItemEquipment(item: Switchgear): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
            val resultRepository =
                repository.saveItemOpenEquipment(mapper.toSwitchgearEntity(item))
            when (resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success(SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemEquipment(): Flow<List<Switchgear>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toSwitchgear(it) } ?: emptyList()
        }
    }

    override suspend fun getItemEquipment(id: String): Flow<Switchgear?> {
        return repository.getItemEquipment(id).map { entities ->
            entities?.let { mapper.toSwitchgear(entities) }
        }
    }
}