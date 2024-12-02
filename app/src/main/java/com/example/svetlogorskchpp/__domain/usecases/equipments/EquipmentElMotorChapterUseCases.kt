package com.example.svetlogorskchpp.__domain.usecases.equipments

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow

interface EquipmentElMotorChapterUseCases {
    fun getIsRep(): Flow<List<ElectricalEquipment.ElMotor>>
    fun getElMotorGenCategory(generalCategory: ElGeneralCategory): Flow<List<ElectricalEquipment.ElMotor>>
}