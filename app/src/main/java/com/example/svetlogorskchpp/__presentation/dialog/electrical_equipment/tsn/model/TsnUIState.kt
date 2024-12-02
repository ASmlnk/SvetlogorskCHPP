package com.example.svetlogorskchpp.__presentation.dialog.electrical_equipment.tsn.model

import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage

data class TsnUIState(
    val id: String = "",
    val name: String = "",
    val panelMcp: String = "",
    val type: String = "",
    val parameterType: String = "",
    val transcriptType: String = "",
    val additionally: String = "",
    val isSpare: Boolean = false,
    val powerSupplyId: String = "",
    val powerSupplyCell: String = "",
    val powerSupplyName: String = "",
    val voltage: Voltage = Voltage.KV,
    val typeSwitch: String = "",
    val typeInsTr: String = "",
    val automation: String = "",
    val apv: String = "",
    val phaseProtection: List<String> = emptyList(),
    val earthProtection: List<String> = emptyList(),
    val isAccessEdit: Boolean = false
    )