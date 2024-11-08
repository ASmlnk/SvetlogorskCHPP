package com.example.svetlogorskchpp.__domain.usecases.equipments

import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow

interface EquipmentConsumerUseCases <out T>{
    fun getEquipmentPowerSupply (idPowerSupply: String): Flow<ElectricalEquipment.Tr?>
}