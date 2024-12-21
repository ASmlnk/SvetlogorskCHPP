package com.example.svetlogorskchpp.__domain.usecases.equipments.item.mechanical

import com.example.svetlogorskchpp.__data.database.equipment.mechanism_info.MechanismInfoEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.equipment.MechanismInfoMapper
import com.example.svetlogorskchpp.__domain.model.equipment.MechanismInfo
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentMechanismInfoUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<MechanismInfoEntity>,
    private val mapper: MechanismInfoMapper,
    private val networkUseCases: NetworkAvailableUseCase
): EquipmentsUseCases<MechanismInfo> {
    override suspend fun saveItemEquipment(item: MechanismInfo): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
            val resultRepository =
                repository.saveItemOpenEquipment(mapper.toMechanismInfoEntity(item))
            when (resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success(SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemEquipment(): Flow<List<MechanismInfo>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toMechanismInfo(it) } ?: emptyList()
        }
    }

    override suspend fun getItemEquipment(id: String): Flow<MechanismInfo?> {
        return repository.getItemEquipment(id).map { entities ->
            entities?.let { mapper.toMechanismInfo(entities) }
        }
    }
}