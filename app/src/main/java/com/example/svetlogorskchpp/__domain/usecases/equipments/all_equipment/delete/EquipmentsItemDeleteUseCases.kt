package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.delete

import com.example.svetlogorskchpp.__domain.OperationResult
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment

interface EquipmentsItemDeleteUseCases  {
    suspend fun deleteItem(item: ElectricalEquipment): OperationResult<String>
}