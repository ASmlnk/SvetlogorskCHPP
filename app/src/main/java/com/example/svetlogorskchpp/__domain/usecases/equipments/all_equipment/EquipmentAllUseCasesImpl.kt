package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment

import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentElMotorListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentLightingAndOtherListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearTrListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentOpenSwitchgearVlListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentSwitchgearListUseCasesImpl
import com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical.EquipmentTransformerOwnNeedsListUseCasesImpl
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentAllUseCasesImpl @Inject constructor(
    private val vlUseCases: EquipmentOpenSwitchgearVlListUseCasesImpl,
    private val elMotorUseCases: EquipmentElMotorListUseCasesImpl,
    private val lightingAndOtherUseCases: EquipmentLightingAndOtherListUseCasesImpl,
    private val switchgearUseCases: EquipmentSwitchgearListUseCasesImpl,
    private val trUseCases: EquipmentOpenSwitchgearTrListUseCasesImpl,
    private val tsnUseCases: EquipmentTransformerOwnNeedsListUseCasesImpl,
    private val powerSupplyUseCases: EquipmentPowerSupplyUseCases,
    private val consumerUseCases: EquipmentConsumerUseCases,
) : EquipmentAllUseCases {

    override fun getEquipmentsAllFlow(): Flow<List<ElectricalEquipment>> {
        val trFlow = trUseCases.getElectricalEquipments().map { it.sortedBy { it.nameNumber } }
        val tsnFlow = tsnUseCases.getElectricalEquipments().map { it.sortedBy { it.nameNumber } }
        val vlFlow = vlUseCases.getElectricalEquipments().map { it.sortedBy { it.nameEquipment } }
        val elMotorFlow = elMotorUseCases.getElectricalEquipments().map { it.sortedBy { it.name } }
        val lightingAndOtherFlow = lightingAndOtherUseCases.getElectricalEquipments().map { it.sortedBy { it.name } }
        val switchgearFlow = switchgearUseCases.getElectricalEquipments()
        return combine(
            trFlow,
            tsnFlow,
            vlFlow,
            elMotorFlow,
            lightingAndOtherFlow,
        ) { tr, tsn, vl, el, light ->
            tr + tsn + vl + el + light
        }.combine(switchgearFlow) {combinedValues, switchgear ->
            combinedValues + switchgear
        }
    } //для общего поиска

    override fun getEquipmentsAllPowerSupplyFlow(): Flow<List<ElectricalEquipment>> {
       return powerSupplyUseCases.getEquipmentsPowerSupplyAllFlow()
    }

    override fun getEquipmentPowerSupplyFlow(idPowerSupply: String): Flow<List<ElectricalEquipment>> =
        powerSupplyUseCases.getEquipmentPowerSupply(idPowerSupply)

    override fun getEquipmentConsumersFlow(id: String): Flow<List<ElectricalEquipment>> =
        consumerUseCases.getEquipmentConsumer(id)
}