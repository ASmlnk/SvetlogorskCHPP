package com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical

import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentRepository
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentsListUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentTransformerOwnNeedsListUseCasesImpl @Inject constructor(
    private val repository: EquipmentRepository<TransformerOwnNeedsEntity>,
    private val mapper: ElectricalEquipmentListMapper,
) : EquipmentsListUseCases<ElectricalEquipment.Tsn> {

    override fun getElectricalEquipments(): Flow<List<ElectricalEquipment.Tsn>> {
        return repository.getAllItemEquipment().map { entities ->
            entities?.map { mapper.toElectricalEquipmentTsn(it) }
                ?: emptyList()
        }
    }

    override fun getSearchElectricalEquipment(
        searchQuery: String,
        prefixQuery: String,
    ): Flow<List<ElectricalEquipment.Tsn>> {
        return repository.getSearchElectricalEquipment(searchQuery,prefixQuery).map { entities ->
            entities.map { mapper.toElectricalEquipmentTsn(it) }

        }
    }
}