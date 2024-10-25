package com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.open_switchgear

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.repository.electrical_equipment.open_switchgear.OpenSwitchgearRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear.OpenSwitchgearVlMapper
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OpenSwitchgearVLUseCasesImpl @Inject constructor(
    private val repository: OpenSwitchgearRepository<OpenSwitchgearVlEntity>,
    private val mapper: OpenSwitchgearVlMapper,
) : OpenSwitchgearUseCases<OpenSwitchgearVl> {
    override suspend fun saveItemOpenSwitchgear(itemOpSw: OpenSwitchgearVl) {
        repository.saveItemOpenSwitchgear(mapper.toOpenSwitchgearVlEntity(itemOpSw))
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