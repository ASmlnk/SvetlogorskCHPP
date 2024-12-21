package com.example.svetlogorskchpp.__data.repository.equipment

import kotlinx.coroutines.flow.Flow

interface EquipmentConsumerRepository <E> {
    fun getItemEntityConsumerFlow(id: String): Flow<List<E>>
}