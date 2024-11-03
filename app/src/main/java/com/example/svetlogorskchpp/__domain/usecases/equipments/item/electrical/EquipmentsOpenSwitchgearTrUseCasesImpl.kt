package com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearTrMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearTr
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentsOpenSwitchgearTrUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<OpenSwitchgearTrEntity>,
    private val mapper: OpenSwitchgearTrMapper,
    private val networkUseCases: NetworkAvailableUseCase
) : EquipmentsUseCases<OpenSwitchgearTr> {
    override suspend fun saveItemEquipment(item: OpenSwitchgearTr): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
           val resultRepository =  repository.saveItemOpenEquipment(mapper.toOpenSwitchgearTrEntity(item))
            when(resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success (SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemEquipment(): Flow<List<OpenSwitchgearTr>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toOpenSwitchgearTr(it) } ?: emptyList()
        }
    }

    override suspend fun getItemEquipment(id: String): Flow<OpenSwitchgearTr?> {
        return repository.getItemEquipment(id).map { openSwitchgearVlEntity ->
            openSwitchgearVlEntity?.let { mapper.toOpenSwitchgearTr(openSwitchgearVlEntity) } }
    }
}