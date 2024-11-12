package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentConsumerUseCases @Inject constructor(
    private val mapper: ElectricalEquipmentListMapper,
    private val repositoryTg: EquipmentConsumerRepository<TurboGeneratorEntity>,
    private val repositoryTsn: EquipmentConsumerRepository<TransformerOwnNeedsEntity>,
) {

    fun getEquipmentConsumet (id: String): Flow<List<ElectricalEquipment>> {
        val tgFlow = repositoryTg.getItemEntityConsumerFlow(id).map { entity ->
            entity?.let {
                mapper.toElectricalEquipmentTg(it)
            }
        }
        val tsnFlow = repositoryTsn.getItemEntityConsumerFlow(id).map { entity ->
            entity?.let {
                mapper.toElectricalEquipmentTsn(it)
            }
        }
        return combine(tgFlow, tsnFlow) { tg, tsn ->
            val list = mutableListOf<ElectricalEquipment>()
            tg?.let { list.add(tg) }
            tsn?.let { list.add(tsn) }
            list
        }
    }
}