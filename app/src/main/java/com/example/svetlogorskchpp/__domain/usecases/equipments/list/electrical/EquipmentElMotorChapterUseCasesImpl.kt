package com.example.svetlogorskchpp.__domain.usecases.equipments.list.electrical

import com.example.svetlogorskchpp.__data.repository.equipment.EquipmentElMotorChapterRepository
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.ElectricalEquipmentListMapper
import com.example.svetlogorskchpp.__domain.usecases.equipments.EquipmentElMotorChapterUseCases
import com.example.svetlogorskchpp.__presentation.electrical_equipment.model.ElectricalEquipment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipmentElMotorChapterUseCasesImpl @Inject constructor(
    private val chapterRepository: EquipmentElMotorChapterRepository,
    private val mapper: ElectricalEquipmentListMapper
): EquipmentElMotorChapterUseCases {
    override fun getIsRep(): Flow<List<ElectricalEquipment.ElMotor>> {
        return chapterRepository.getIsRep().map { entities ->
            entities?.map { mapper.toElectricalEquipmentElMotor(it) }
                ?: emptyList()
        }
    }

    override fun getElMotorGenCategory(generalCategory: ElGeneralCategory): Flow<List<ElectricalEquipment.ElMotor>> {
        return chapterRepository.getElMotorGenCategory(generalCategory.name).map { entities ->
            entities?.map { mapper.toElectricalEquipmentElMotor(it) }
                ?: emptyList()
        }
    }
}