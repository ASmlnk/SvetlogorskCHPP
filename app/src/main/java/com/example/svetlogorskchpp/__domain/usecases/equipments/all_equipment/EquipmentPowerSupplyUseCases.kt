package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentPowerSupplyUseCases @Inject constructor(
    private val mapper: ElectricalEquipmentListMapper,
    private val repositoryTr: EquipmentRepository<OpenSwitchgearTrEntity>,
    private val repositoryTsn: EquipmentRepository<TransformerOwnNeedsEntity>,
) {

    fun getEquipmentPowerSupply(idPowerSupply: String): Flow<List<ElectricalEquipment>> {
        val trFlow = repositoryTr.getItemEquipment(idPowerSupply).map { entity ->
            entity?.let {
                mapper.toElectricalEquipmentTr(it)
            }
        }
        val tsnFlow = repositoryTsn.getItemEquipment(idPowerSupply).map { entity ->
            entity?.let {
                mapper.toElectricalEquipmentTsn(it)
            }
        }
        return combine(trFlow, tsnFlow) { tr, tsn ->
            val list = mutableListOf<ElectricalEquipment>()
            tr?.let { list.add(tr) }
            tsn?.let { list.add(tsn) }
            list
        }
    }
}