package com.example.svetlogorskchpp.__data.repository.electrical_equipment.open_switchgear

import kotlinx.coroutines.flow.Flow

interface OpenSwitchgearRepository <T> {
    suspend fun saveItemOpenSwitchgear(openSwitchgearVlEntity: T)
    fun getAllItemOpenSwitchgear(): Flow<List<T>?>
    fun getItemOpenSwitchgear(id: String): Flow<T?>
    suspend fun clearTable()
}