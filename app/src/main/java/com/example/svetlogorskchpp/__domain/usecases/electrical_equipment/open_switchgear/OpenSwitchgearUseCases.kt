package com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.open_switchgear

import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import com.example.svetlogorskchpp.__domain.OperationResult
import kotlinx.coroutines.flow.Flow

interface  OpenSwitchgearUseCases <T> {
    suspend fun saveItemOpenSwitchgear (itemOpSw: T) : OperationResult<String>
    fun getAllItemOpenSwitchgear(): Flow<List<T>>
    suspend fun getItemOpenSwitchgear(id: String): Flow<T?>
}