package com.example.svetlogorskchpp.__presentation.equipment.mechanism_info.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import java.util.UUID

data class MechanismInfoUIState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val info: String = "",
    val category: ElGeneralCategory = ElGeneralCategory.OTHER,
    val additionally: String = ""
) {
}