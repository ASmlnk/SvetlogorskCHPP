package com.example.svetlogorskchpp.__data.repository.equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import kotlinx.coroutines.flow.Flow

interface EquipmentConsumerRepository <T> {
    fun getItemEntityConsumerFlow(id: String): Flow<T?>
}