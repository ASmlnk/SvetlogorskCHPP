package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
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
    private val repositorySwitchgear: EquipmentConsumerRepository<SwitchgearEntity>,
    private val repositoryElMotor: EquipmentConsumerRepository<ElMotorEntity>,
    private val repositoryLighting: EquipmentConsumerRepository<LightingAndOtherEntity>
) {

    fun getEquipmentConsumer(id: String): Flow<List<ElectricalEquipment>> {
        val tgFlow = repositoryTg.getItemEntityConsumerFlow(id).map { entity ->
            entity.map { mapper.toElectricalEquipmentTg(it) }
        }
        val tsnFlow = repositoryTsn.getItemEntityConsumerFlow(id).map { entity ->
            entity.map { mapper.toElectricalEquipmentTsn(it) }
        }
        val switchgearFlow = repositorySwitchgear.getItemEntityConsumerFlow(id).map { entity ->
            entity.map { mapper.toElectricalEquipmentSwitchgear(it, id) }
        }
        val elMotorFlow = repositoryElMotor.getItemEntityConsumerFlow(id).map { entity ->
            entity.map { mapper.toElectricalEquipmentElMotor(it) }
        }
        val lightingAndOtherFlow = repositoryLighting.getItemEntityConsumerFlow(id).map { entity ->
            entity.map { mapper.toElectricalEquipmentLightOther(it) }
        }
        return combine(
            tgFlow,
            tsnFlow,
            switchgearFlow,
            elMotorFlow,
            lightingAndOtherFlow
        ) { tg, tsn, switchgear, elMotor, lightingAndOther ->
            tg + tsn + switchgear + elMotor + lightingAndOther
        }
    }
}