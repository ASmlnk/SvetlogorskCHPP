package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.el_motor.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory

data class ElMotorDialogUIState(
    val name: String = "",
    val powerSupplyCell: String = "",
    val powerSupplyName: String = "",
    val automation: String = "",
    val phaseProtection: List<String> = emptyList(),
    val earthProtection: List<String> = emptyList(),
    val additionallyRza: String = "",
    val category:  ElCategory = ElCategory.OTHER,
    val generalCategory: String = "",
    val typeSwitch: String = "",
    val typeInsTr: String = "",
    val additionally: String = "",
    val isRep: Boolean = false,
    val typeRep: String = "",
    val controlCircuits: String = "",
    val powerEl: String = "",
    val voltage: String = "",
    val i: String = "",
    val n: String = "",
    val typeEl: String = "",
    val mechanismType: String = "",
    val mechanismPerformance: String = "",
    val mechanismPressure: String = "",
    val mechanismN: String = "",
    val mechanismH: String = "",
    val mechanismPowerN: String = "",
    val mechanismAdditionally: String = "",
    val isAccessEdit: Boolean = false
)
