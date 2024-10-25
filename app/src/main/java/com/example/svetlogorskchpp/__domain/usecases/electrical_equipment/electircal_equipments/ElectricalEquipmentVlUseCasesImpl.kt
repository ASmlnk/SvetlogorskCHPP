package com.example.svetlogorskchpp.__domain.usecases.electrical_equipment.electircal_equipments

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.repository.electrical_equipment.open_switchgear.OpenSwitchgearRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentMapper
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ElectricalEquipmentVlUseCasesImpl @Inject constructor(
    private val repository: OpenSwitchgearRepository<OpenSwitchgearVlEntity>,
    private val mapper: ElectricalEquipmentMapper,
) : ElectricalEquipmentsUseCases<ElectricalEquipment.Vl> {

    override fun getElectricalEquipments(): Flow<List<ElectricalEquipment.Vl>> {
        return repository.getAllItemOpenSwitchgear().map { entities ->
            entities?./*filter { it.isVl }?.*/map { mapper.toElectricalEquipmentVl(it) }
                ?.sortedBy { it.cell }?.sortedByDescending { it.voltageOry.int }
                ?: emptyList()
        }
    }
}