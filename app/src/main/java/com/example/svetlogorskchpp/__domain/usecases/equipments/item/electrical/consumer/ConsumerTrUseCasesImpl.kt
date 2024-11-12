package com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.consumer

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__domain.usecases.equipments.ConsumerUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConsumerTrUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<OpenSwitchgearTrEntity>,
    private val mapper: ElectricalEquipmentListMapper,
) : ConsumerUseCases<ElectricalEquipment.Tr> {

    override fun getItemEquipmentConsumerFlow(id: String): Flow<ElectricalEquipment.Tr?> {
        return repository.getItemEquipment(id).map { openSwitchgearTrEntity ->
            openSwitchgearTrEntity?.let {
                mapper.toElectricalEquipmentTr(it)
            }
        }
    }
}