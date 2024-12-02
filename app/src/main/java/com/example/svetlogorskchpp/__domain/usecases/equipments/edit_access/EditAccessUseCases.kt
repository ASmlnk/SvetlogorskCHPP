package com.example.svetlogorskchpp.__domain.usecases.equipments.edit_access

import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.EditAccessResult
import kotlinx.coroutines.flow.Flow

interface EditAccessUseCases {
    suspend fun equalsEditAccessPrefFb()
    fun getIsEditAccess(): Flow<Boolean>
    suspend fun setKdEditAccess(kd: String): OperationResult<EditAccessResult>
}