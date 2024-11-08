package com.example.svetlogorskchpp.__domain.model.electrical_equipment

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class TransformerOwnNeeds(
    val id: String,
    val name: String,
    val panelMcp: String,
    val type: String,
    val parameterType: String,
    val transcriptType: String,
    val additionally: String,
    val isSpare: Boolean,
    val powerSupplyId: String,
    val powerSupplyCell: String,
    val powerSupplyName: String,
    val voltage: Voltage,
    val typeSwitch: String,
    val typeInsTr: String,
    val automation: String,
    val apv: String,
    val phaseProtection: List<String>,
    val earthProtection: List<String>,
)
