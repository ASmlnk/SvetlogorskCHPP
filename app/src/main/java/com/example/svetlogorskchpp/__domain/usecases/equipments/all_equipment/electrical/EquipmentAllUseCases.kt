package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.electrical

import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow

interface EquipmentAllUseCases {
    fun getEquipmentsAllFlow(): Flow<List<ElectricalEquipment>>
    fun getEquipmentsAllPowerSupplyFlow(): Flow<List<ElectricalEquipment>>
    fun getEquipmentPowerSupplyFlow(idPowerSupply: String): Flow<List<ElectricalEquipment>>
    fun getEquipmentConsumersFlow(id: String): Flow<List<ElectricalEquipment>>
    fun getSearchElectricalEquipment(searchQuery: String, prefixQuery: String): Flow<List<ElectricalEquipment>>
}