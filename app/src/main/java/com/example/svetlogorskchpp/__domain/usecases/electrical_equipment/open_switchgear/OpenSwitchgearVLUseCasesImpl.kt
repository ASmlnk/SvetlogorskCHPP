package com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.open_switchgear

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__data.repository.electrical_equipment.open_switchgear.OpenSwitchgearRepository
import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearVlMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import com.example.svetlogorskchpp.__domain.usecases.NetworkAvailableUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OpenSwitchgearVLUseCasesImpl @Inject constructor(
    private val repository: OpenSwitchgearRepository<OpenSwitchgearVlEntity>,
    private val mapper: OpenSwitchgearVlMapper,
    private val networkUseCases: NetworkAvailableUseCase
) : OpenSwitchgearUseCases<OpenSwitchgearVl> {
    override suspend fun saveItemOpenSwitchgear(itemOpSw: OpenSwitchgearVl): OperationResult<String> {
        return if (networkUseCases.isNetworkAvailable()) {
           val resultRepository =  repository.saveItemOpenSwitchgear(mapper.toOpenSwitchgearVlEntity(itemOpSw))
            when(resultRepository) {
                SuccessResultFirebase.UPDATE_OK -> OperationResult.Success (SuccessResultFirebase.UPDATE_OK.result)
                SuccessResultFirebase.UPDATE_ERROR -> OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
            }
        } else {
            OperationResult.Error(SuccessResultFirebase.UPDATE_ERROR.result)
        }
    }

    override fun getAllItemOpenSwitchgear(): Flow<List<OpenSwitchgearVl>> {
        return repository.getAllItemOpenSwitchgear().map { entities ->
            entities?.map { mapper.toOpenSwitchgearVl(it) } ?: emptyList()
        }
    }

    override suspend fun getItemOpenSwitchgear(id: String): Flow<OpenSwitchgearVl?> {
        return repository.getItemOpenSwitchgear(id).map { openSwitchgearVlEntity ->
            openSwitchgearVlEntity?.let { mapper.toOpenSwitchgearVl(openSwitchgearVlEntity) } }
    }
}