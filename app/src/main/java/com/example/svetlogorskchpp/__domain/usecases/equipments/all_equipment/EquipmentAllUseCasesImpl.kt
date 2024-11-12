package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment

import com.example.svetlogorskchpp.__domain.usecases.equipments.item.electrical.consumer.ConsumerTrUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearTrListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentTransformerOwnNeedsListUseCasesImpl
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentAllUseCasesImpl @Inject constructor(
    private val trUseCases: EquipmentOpenSwitchgearTrListUseCasesImpl,
    private val tsnUseCases: EquipmentTransformerOwnNeedsListUseCasesImpl,
    private val powerSupplyUseCases: EquipmentPowerSupplyUseCases,
    private val consumerUseCases: EquipmentConsumerUseCases,
) : EquipmentAllUseCases {
    override fun getEquipmentsAllFlow(): Flow<List<ElectricalEquipment>> {
        val trFlow = trUseCases.getElectricalEquipments().map { it.sortedBy { it.nameNumber } }
        val tsnFlow = tsnUseCases.getElectricalEquipments().map { it.sortedBy { it.nameNumber } }
        return combine(trFlow, tsnFlow) { tr, tsn ->
            tr + tsn
        }
    }

    override fun getEquipmentPowerSupplyFlow(idPowerSupply: String): Flow<List<ElectricalEquipment>> =
        powerSupplyUseCases.getEquipmentPowerSupply(idPowerSupply)

    override fun getEquipmentConsumersFlow(id: String): Flow<List<ElectricalEquipment>> =
        consumerUseCases.getEquipmentConsumet(id)
}