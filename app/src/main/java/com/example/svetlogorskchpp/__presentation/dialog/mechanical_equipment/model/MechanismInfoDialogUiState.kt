package com.example.svetlogorskchpp.__presentation.dialog.mechanical_equipment.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory

data class MechanismInfoDialogUiState(
    val name: String = "",
    val info: String = "",
    val category: ElGeneralCategory = ElGeneralCategory.OTHER,
    val additionally: String = "",
    val isAccessEdit: Boolean = false
)
