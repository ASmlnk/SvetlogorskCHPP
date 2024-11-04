package com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentOpenSwitchgearTrListUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<OpenSwitchgearTrEntity>,
    private val mapper: ElectricalEquipmentListMapper,
) : EquipmentsListUseCases<ElectricalEquipment.Tr> {

    override fun getElectricalEquipments(): Flow<List<ElectricalEquipment.Tr>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toElectricalEquipmentTr(it) }
                ?: emptyList()
        }
    }
}