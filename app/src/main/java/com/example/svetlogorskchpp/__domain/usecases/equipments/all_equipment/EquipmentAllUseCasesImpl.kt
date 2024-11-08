package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment


import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentConsumerUseCases
import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.EquipmentConsumerTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearTrListUseCasesImpl
import com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.power_supply_selection.view_model.PowerSupplySelectionViewModel
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

class EquipmentAllUseCasesImpl @Inject constructor(
    private val trUseCases: EquipmentOpenSwitchgearTrListUseCasesImpl,
    private val trConsumerUseCases: EquipmentConsumerTrUseCasesImpl
): EquipmentAllUseCases {
    override fun getEquipmentsFlow(): Flow<List<ElectricalEquipment>> {
        return trUseCases.getElectricalEquipments().map { it.sortedBy { it.nameNumber } }
    }

    override fun getEquipmentFlow(id: String): Flow<List<ElectricalEquipment>> {
        return trConsumerUseCases.getEquipmentPowerSupply(id).scan(mutableListOf<ElectricalEquipment>()) { acc, value ->
            value?.let {
                acc.add(it)
            }
            acc
        }.distinctUntilChanged()
    }
}