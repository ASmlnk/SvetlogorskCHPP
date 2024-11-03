package com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentMapper
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentOpenSwitchgearVlListUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<OpenSwitchgearVlEntity>,
    private val mapper: ElectricalEquipmentMapper,
) : EquipmentsListUseCases<ElectricalEquipment.Vl> {

    override fun getElectricalEquipments(): Flow<List<ElectricalEquipment.Vl>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?./*filter { it.isVl }?.*/map { mapper.toElectricalEquipmentVl(it) }
                ?.sortedBy { it.cell }?.sortedByDescending { it.voltage.int }
                ?: emptyList()
        }
    }
}