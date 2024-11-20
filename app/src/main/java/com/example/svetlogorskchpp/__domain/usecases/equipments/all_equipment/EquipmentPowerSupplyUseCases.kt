package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
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
    private val repositorySwitchgear: EquipmentRepository<SwitchgearEntity>
) {

    fun getEquipmentsPowerSupplyAllFlow(): Flow<List<ElectricalEquipment>> {
        val trFlow = repositoryTr.getAllItemEquipment().map { entities ->
            entities?.let {
                entities.map { mapper.toElectricalEquipmentTr(it) }
            }
        }
        val tsnFlow = repositoryTsn.getAllItemEquipment().map { entities ->
            entities?.let {
                entities.map { mapper.toElectricalEquipmentTsn(it) }
            }
        }
        val switchgearFlow = repositorySwitchgear.getAllItemEquipment().map { entities ->
            entities?.let {
                entities.map { mapper.toElectricalEquipmentSwitchgear(it) }
            }
        }
        return combine(trFlow, tsnFlow, /*switchgearFlow*/) { tr, tsn, /*sw*/ ->
            val list = mutableListOf<ElectricalEquipment>()
            tr?.let { list.addAll(tr) }
            tsn?.let { list.addAll(tsn) }
            /*sw?.let { list.addAll(sw) }*/
            list
        }
    }

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
        val switchgearFlow = repositorySwitchgear.getItemEquipment(idPowerSupply).map { entity ->
            entity?.let {
                mapper.toElectricalEquipmentSwitchgear(it)
            }
        }
        return combine(trFlow, tsnFlow, switchgearFlow) { tr, tsn, sw ->
            val list = mutableListOf<ElectricalEquipment>()
            tr?.let { list.add(tr) }
            tsn?.let { list.add(tsn) }
            sw?.let { list.add(sw) }
            list
        }
    }
}