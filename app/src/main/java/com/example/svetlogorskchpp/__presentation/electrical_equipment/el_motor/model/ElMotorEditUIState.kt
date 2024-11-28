package com.example.svetlogorskchpp.__presentation.electrical_equipment.el_motor.model

import java.util.UUID

data class ElMotorEditUIState(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val powerSupplyId: String = "",
    val powerSupplyCell: String = "",
    val powerSupplyName: String = "",
    val automation: String = "",
    val additionallyRza: String = "",
    val typeSwitch: String = "",
    val typeInsTr: String = "",
    val additionally: String = "",
    val isRep: Boolean = false,
    val typeRep: String = "",
    val controlCircuits: String = "",
    val powerEl: String = "",
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
)
