package com.example.svetlogorskchpp.__domain.model.equipment

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory

data class MechanismInfo(
    val id: String,
    val name: String,
    val info: String,
    val category: ElGeneralCategory,
    val additionally: String,
    val compositeMechanismId: String,  //id генеральной установки
    val compositeMechanismName: String
)
