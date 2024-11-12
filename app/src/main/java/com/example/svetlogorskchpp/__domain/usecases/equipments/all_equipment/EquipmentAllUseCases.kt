package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment

import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow

interface EquipmentAllUseCases {
    fun getEquipmentsFlow(): Flow<List<ElectricalEquipment>>
    fun getEquipmentPowerSupplyFlow(id: String): Flow<List<ElectricalEquipment>>
}