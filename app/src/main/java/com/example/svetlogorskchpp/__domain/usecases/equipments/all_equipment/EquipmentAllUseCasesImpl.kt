package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment

import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentConsumerTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearTrListUseCasesImpl
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentAllUseCasesImpl @Inject constructor(
    private val trUseCases: EquipmentOpenSwitchgearTrListUseCasesImpl,
    private val trConsumerUseCases: EquipmentConsumerTrUseCasesImpl
): EquipmentAllUseCases {
    override fun getEquipmentsAllFlow(): Flow<List<ElectricalEquipment>> {
        return trUseCases.getElectricalEquipments().map { it.sortedBy { it.nameNumber } }
    }

    override fun getEquipmentPowerSupplyFlow(idPowerSupply: String): Flow<List<ElectricalEquipment>> = flow {
        val listEquipment = mutableListOf<ElectricalEquipment>()
        trConsumerUseCases.getEquipmentPowerSupply(idPowerSupply).collect { item ->
            item?.let { listEquipment.add(item) }
            emit(listEquipment.toList())
        }
    }

    override fun getEquipmentConsumersFlow(id: String): Flow<List<ElectricalEquipment>> {
        TODO("Not yet implemented")
    }
}