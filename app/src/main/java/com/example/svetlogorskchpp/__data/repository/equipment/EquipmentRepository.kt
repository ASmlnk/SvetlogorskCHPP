package com.example.svetlogorskchpp.__data.repository.equipment

import com.example.svetlogorskchpp.__data.model.SuccessResultFirebase
import kotlinx.coroutines.flow.Flow

interface EquipmentRepository <T> {
    suspend fun saveItemOpenEquipment(itemEntity: T) : SuccessResultFirebase
    fun getAllItemEquipment(): Flow<List<T>?>
    fun getItemEquipment(id: String): Flow<T?>
    suspend fun updateLocaleData()
}