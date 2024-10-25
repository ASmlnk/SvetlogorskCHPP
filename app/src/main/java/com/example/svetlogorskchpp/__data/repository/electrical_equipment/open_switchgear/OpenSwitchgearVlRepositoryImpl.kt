package com.example.svetlogorskchpp.__data.repository.electrical_equipment.open_switchgear

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OpenSwitchgearVlRepositoryImpl @Inject constructor(
    private val dao: OpenSwitchgearVlDao,
) : OpenSwitchgearRepository<OpenSwitchgearVlEntity> {

    override suspend fun saveItemOpenSwitchgear(openSwitchgearVlEntity: OpenSwitchgearVlEntity) {
        dao.saveItemOpenSwitchgear(openSwitchgearVlEntity)
    }

    override fun getAllItemOpenSwitchgear(): Flow<List<OpenSwitchgearVlEntity>?> {
        return dao.getAllItemOpenSwitchgear()
    }

    override fun getItemOpenSwitchgear(id: String): Flow<OpenSwitchgearVlEntity?> {
        return dao.getItemOpenSwitchgear(id)
    }

    override suspend fun clearTable() {
        dao.clearTable()
    }

}