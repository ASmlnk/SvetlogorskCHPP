package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.mechanical

import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow

interface EquipmentMechanismAllUseCases {
    fun getSubMechanismFlow(id: String): Flow<List<ElectricalEquipment>>
    fun getAllCompositeMechanismFlow():  Flow<List<ElectricalEquipment>>
    fun getCompositeMechanismFlow(idComposite: String):  Flow<List<ElectricalEquipment>>
}