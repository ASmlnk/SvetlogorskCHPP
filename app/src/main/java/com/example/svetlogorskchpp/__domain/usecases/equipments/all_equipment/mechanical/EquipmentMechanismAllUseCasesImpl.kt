package com.example.svetlogorskchpp.__domain.usecases.equipments.all_equipment.mechanical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.equipment.mechanism_info.MechanismInfoEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentConsumerRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentSubMechanismRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentMechanismAllUseCasesImpl @Inject constructor(
    private val mapper: ElectricalEquipmentListMapper,
    private val repositoryElMotor: EquipmentSubMechanismRepository<ElMotorEntity>,
    private val repositorySubMechanism: EquipmentSubMechanismRepository<MechanismInfoEntity>,
    private val repositoryMechanismInfo: EquipmentRepository<MechanismInfoEntity>,
) : EquipmentMechanismAllUseCases {

    override fun getSubMechanismFlow(id: String): Flow<List<ElectricalEquipment>> {
        val elMotorFlow = repositoryElMotor.getItemsEntitySubMechanismFlow(id).map { entity ->
            entity.map { mapper.toElectricalEquipmentElMotor(it) }
        }
        val mechanismInfoFlow =
            repositorySubMechanism.getItemsEntitySubMechanismFlow(id).map { entity ->
                entity.map { mapper.toElectricalEquipmentMechanismInfo(it) }
            }

        return combine(elMotorFlow, mechanismInfoFlow) { elMotor, mechanismInfo ->
            (elMotor + mechanismInfo).sortedBy { it.name() }
        }
    }

    override fun getAllCompositeMechanismFlow(): Flow<List<ElectricalEquipment>> {
        return repositoryMechanismInfo.getAllItemEquipment().map { entity ->
            entity?.map { mapper.toElectricalEquipmentMechanismInfo(it) } ?: emptyList()
        }
    }

    override fun getCompositeMechanismFlow(idComposite: String): Flow<List<ElectricalEquipment>> {

        return repositoryMechanismInfo.getItemEquipment(idComposite).map { entity ->
            val list = mutableListOf<ElectricalEquipment>()
            entity?.let { mapper.toElectricalEquipmentMechanismInfo(it) }.also { it?.let { list.add(it) } }
            list
        }
    }
}