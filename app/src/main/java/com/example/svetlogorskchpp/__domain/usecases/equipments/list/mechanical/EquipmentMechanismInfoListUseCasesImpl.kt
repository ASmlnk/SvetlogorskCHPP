package com.example.svetlogorskchpp.__domain.usecases.equipments.list.mechanical

import com.example.svetlogorskchpp.__data.database.equipment.mechanism_info.MechanismInfoEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentMechanismInfoListUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<MechanismInfoEntity>,
    private val mapper: ElectricalEquipmentListMapper
) : EquipmentsListUseCases<ElectricalEquipment.MechanismInfo>{
    override fun getElectricalEquipments(): Flow<List<ElectricalEquipment.MechanismInfo>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toElectricalEquipmentMechanismInfo(it) }
                ?: emptyList()
        }
    }

    override fun getSearchElectricalEquipment(
        searchQuery: String,
        prefixQuery: String,
    ): Flow<List<ElectricalEquipment.MechanismInfo>> {
        return repository.getSearchElectricalEquipment(searchQuery,prefixQuery).map { entities ->
            entities.map { mapper.toElectricalEquipmentMechanismInfo(it) }

        }
    }
}