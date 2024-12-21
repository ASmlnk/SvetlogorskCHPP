package com.example.svetlogorskchpp.__data.repository.equipment

import kotlinx.coroutines.flow.Flow

interface EquipmentSubMechanismRepository <E> {
    fun getItemsEntitySubMechanismFlow(id: String): Flow<List<E>>
}