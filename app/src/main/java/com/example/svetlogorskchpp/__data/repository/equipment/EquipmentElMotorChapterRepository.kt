package com.example.svetlogorskchpp.__data.repository.equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import kotlinx.coroutines.flow.Flow

interface EquipmentElMotorChapterRepository {
    fun getIsRep(): Flow<List<ElMotorEntity>?>
    fun getElMotorGenCategory(generalCategory: String): Flow<List<ElMotorEntity>?>
}