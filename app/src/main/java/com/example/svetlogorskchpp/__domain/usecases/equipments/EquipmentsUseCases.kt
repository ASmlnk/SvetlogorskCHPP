package com.example.svetlogorskchpp.__domain.usecases.equipments

import com.example.svetlogorskchpp.__domain.OperationResult
import kotlinx.coroutines.flow.Flow

interface  EquipmentsUseCases <T> {
    suspend fun saveItemEquipment (item: T) : OperationResult<String>
    fun getAllItemEquipment(): Flow<List<T>>
    suspend fun getItemEquipment(id: String): Flow<T?>
}