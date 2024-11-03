package com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearVlMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentsOpenSwitchgearVLUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<OpenSwitchgearVlEntity>,
    private val mapper: OpenSwitchgearVlMapper,
    private val networkUseCases: NetworkAvailableUseCase
) : EquipmentsUseCases<OpenSwitchgearVl> {
    override suspend fun saveItemEquipment(item: OpenSwitchgearVl): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
           val resultRepository =  repository.saveItemOpenEquipment(mapper.toOpenSwitchgearVlEntity(item))
            when(resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success (SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemEquipment(): Flow<List<OpenSwitchgearVl>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toOpenSwitchgearVl(it) } ?: emptyList()
        }
    }

    override suspend fun getItemEquipment(id: String): Flow<OpenSwitchgearVl?> {
        return repository.getItemEquipment(id).map { openSwitchgearVlEntity ->
            openSwitchgearVlEntity?.let { mapper.toOpenSwitchgearVl(openSwitchgearVlEntity) } }
    }
}