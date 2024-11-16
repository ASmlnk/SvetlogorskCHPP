package com.example.svetlogorskchpp.__domain.model.electrical_equipment

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class ElMotor(
    val id: String,
    val name: String,
    val powerSupplyId: String,
    val powerSupplyCell: String,
    val powerSupplyName: String,
    val automation: String,
    val phaseProtection: List<String>,
    val earthProtection: List<String>,
    val additionallyRza: String,
    val category: String, //?
    val generalCategory: String,  //?
    val typeSwitch: String,
    val typeInsTr: String,
    val additionally: String,
    val isRep: Boolean,
    val typeRep: String,
    val controlCircuits: String,
    val powerEl: String,
    val voltage: Voltage,
    val i: String,
    val n: String,
    val typeEl: String,
    val mechanismType: String,
    val mechanismPerformance: String,
    val mechanismPressure: String,
    val mechanismN: String
)

