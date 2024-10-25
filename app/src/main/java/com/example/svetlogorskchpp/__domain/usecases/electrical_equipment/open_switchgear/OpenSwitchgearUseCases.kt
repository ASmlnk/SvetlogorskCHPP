package com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.open_switchgear

import kotlinx.coroutines.flow.Flow

interface  OpenSwitchgearUseCases <T> {
    suspend fun saveItemOpenSwitchgear (itemOpSw: T)
    fun getAllItemOpenSwitchgear(): Flow<List<T>>
    suspend fun getItemOpenSwitchgear(id: String): Flow<T?>
}